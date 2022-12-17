import { Card, Center, Flex, Select } from "@mantine/core";
import { IconBook } from "@tabler/icons";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { toast } from "react-toastify";
import { getUniCourses } from "../../api/Student/CourseService";
import { evaluateCourse, getStudentPastCourseEval } from "../../api/Student/EvaluationService";
import { getStudent } from "../../api/StudentService";
import Evaluation from "../../components/evaluation/Evaluation";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useUser } from "../../provider/UserProvider";
import { StudentAssociatedCoursePastEvaluationItem } from "../../types";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";


const EvaluateCoursesPage = () => {
    const axiosSecure = useAxiosSecure()
    const { user } = useUser()
    const queryClient = useQueryClient()
    const { data: dataStudent, isError: isStudentError, isLoading: isStudentLoading } = useQuery({
        queryFn: () => getStudent(axiosSecure, user?.bilkentId!),
        queryKey: ["get_student"]
    })
    const { data: dataCourses, isError: isCoursesError, isLoading: isCoursesLoading } = useQuery({
        queryFn: () => getUniCourses(axiosSecure, dataStudent?.data.hostUni.id!),
        queryKey: ["get_uni_courses", dataStudent],
        enabled: !!dataStudent
    })

    const { mutate: mutateGetPastCourseEval, data: dataEval, isLoading: isEvalLoading } = useMutation({
        mutationKey: ['get_past_eval'],
        mutationFn: (selectedId: string) => getStudentPastCourseEval(axiosSecure, user?.bilkentId!, selectedId),
        onSuccess: dataEval => {
            setGivenRating(dataEval.data.rate);
            setCurrentEvaluation(dataEval.data.comment);
        },
    })

    const { mutate: mutateSaveEvalUni, data: userSaveData, isLoading: isSaveUniLoading } = useMutation({
        mutationKey: ['saveUniEval'],
        mutationFn: (newEval: StudentAssociatedCoursePastEvaluationItem) => evaluateCourse(axiosSecure, newEval),
        onSuccess: () => toast.success(`Evaluation saved.`),
        onError: () => toast.error("Evaluation save failed.")
    })

    const { mutate: mutateSubmitEvalUni, data: userSubmitData, isLoading: isSubmitUniLoading } = useMutation({
        mutationKey: ['submitUniEval'],
        mutationFn: (newEval: StudentAssociatedCoursePastEvaluationItem) => evaluateCourse(axiosSecure, newEval),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['get_past_eval'] })
            toast.success(`Evaluation submitted.`);},
        onError: () => toast.error("Evaluation submit failed.")
    })


    const [searchedCourse, setSearchedCourse] = useState('');
    const [selectedCourseName, setSelectedCourse] = useState<string | null>(null);
    const [selectedCourseId, setSelectedCourseId] = useState<string>('');
    const [currentEvaluation, setCurrentEvaluation] = useState("Waiting for response...");
    const [givenRating, setGivenRating] = useState(0);
    const description = `You can evaluate ${selectedCourseName} in terms of difficulty, assignments, instructors, exams, course content and material etc.`;

    const newSaveEval: StudentAssociatedCoursePastEvaluationItem = {
        course_id: selectedCourseId,
        author_id: user?.bilkentId!,
        comment: currentEvaluation || "",
        rate: givenRating || 0,
        eval_status: "SAVED"
    }

    const newSubmitEval: StudentAssociatedCoursePastEvaluationItem = {
        course_id: selectedCourseId,
        author_id: user?.bilkentId!,
        comment: currentEvaluation || "",
        rate: givenRating || 0,
        eval_status: "SUBMITTED"
    }
    const saveEval = () => {
        mutateSaveEvalUni(newSaveEval);
    }
    const submitEval = () => {
        mutateSubmitEvalUni(newSubmitEval);
    }
    if (isCoursesLoading || isEvalLoading) {
        return (
            <LoadingPage />
        )
    }

    if (isCoursesError || isStudentError) {
        return (
            <ErrorPage />
        )
    }
    const courses = dataCourses.data;

    const availableCourseNames: Array<string> = courses.map((course) => {
        return course.courseName;
    });

    return (
        <>
            <Select
                value={selectedCourseName}
                searchable
                placeholder="Choose a course to evaluate"
                allowDeselect
                label={"Choose a course to evaluate"}
                nothingFound="No course found"
                onSearchChange={setSearchedCourse}
                onChange={(value) => {
                    setSelectedCourse(value);
                    const id = courses.find(course => course.courseName === value)?.courseId!;
                    setSelectedCourseId(id);
                    mutateGetPastCourseEval(id);
                }}
                searchValue={searchedCourse}
                data={availableCourseNames} />
            {selectedCourseName != null &&
                <Evaluation givenRating={givenRating ?? 0}
                    evaluationName={selectedCourseName ?? ''}
                    description={description}
                    currentEvaluation={currentEvaluation ?? ''}
                    setGivenRating={setGivenRating}
                    setCurrentEvaluation={setCurrentEvaluation}
                    emptySymbol={<IconBook />}
                    fullSymbol={<IconBook color="#1971c2" />}
                    editable={(dataEval?.data.eval_status !== "SUBMITTED")}
                    saveEval={saveEval} submitEval={submitEval}></Evaluation>}
        </>
    );
}

export default EvaluateCoursesPage;