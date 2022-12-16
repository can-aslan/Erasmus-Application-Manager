import { Card, Center, Flex, Select } from "@mantine/core";
import { IconBook } from "@tabler/icons";
import { useMutation, useQuery } from "@tanstack/react-query";
import { MouseEvent, useState } from "react";
import { toast } from "react-toastify";
import { getUniCourses } from "../../api/Student/CourseService";
import { evaluateCourse, getStudentPastCourseEval } from "../../api/Student/EvaluationService";
import { getStudent } from "../../api/StudentService";
import Evaluation from "../../components/evaluation/Evaluation";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useCourses } from "../../hooks/useCourses";
import { useUser } from "../../provider/UserProvider";
import { Course, HostCourse, StudentAssociatedPastEvaluationItem } from "../../types";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";


const EvaluateCoursesPage = () => {
    const axiosSecure = useAxiosSecure()
    const { user } = useUser()

    const { data: dataStudent, isError: isStudentError, isLoading: isStudentLoading } = useQuery({
        queryFn: () => getStudent(axiosSecure, user?.bilkentId!),
        queryKey: ["get_student"]
    })
    const { data: dataCourses, isError: isCoursesError, isLoading: isCoursesLoading } = useQuery({
        queryFn: () => getUniCourses(axiosSecure, dataStudent?.data.hostUni.id!),
        queryKey: ["get_uni_courses"]
    })

    const { mutate: mutateGetPastCourseEval, data: dataEval, isLoading: isEvalLoading } = useMutation({
        mutationKey: ['get_past_eval'],
        mutationFn: (selectedId: string) => getStudentPastCourseEval(axiosSecure, user?.bilkentId!, selectedId),
        onSuccess: () => { },
        onError: () => { }
    })

    const { mutate: mutateSaveEvalUni, data: userSaveData, isLoading: isSaveUniLoading } = useMutation({
        mutationKey: ['saveUniEval'],
        mutationFn: (newEval: StudentAssociatedPastEvaluationItem) => evaluateCourse(axiosSecure, newEval),
        onSuccess: () => toast.success(`Evaluation saved.`),
        onError: () => toast.error("Evaluation save failed.")
    })

    const { mutate: mutateSubmitEvalUni, data: userSubmitData, isLoading: isSubmitUniLoading } = useMutation({
        mutationKey: ['submitUniEval'],
        mutationFn: (newEval: StudentAssociatedPastEvaluationItem) => evaluateCourse(axiosSecure, newEval),
        onSuccess: () => toast.success(`Evaluation submitted.`),
        onError: () => toast.error("Evaluation submit failed.")
    })

    const courses = dataCourses?.data;
    const evaluation = dataEval?.data;
    const [searchedCourse, setSearchedCourse] = useState('');
    const [selectedCourseName, setSelectedCourse] = useState<string | null>(null);
    const [selectedCourseId, setSelectedCourseId] = useState<string>('');
    const [currentEvaluation, setCurrentEvaluation] = useState(evaluation?.comment);
    const [givenRating, setGivenRating] = useState(evaluation?.rate);
    const description = `You can evaluate ${selectedCourseName} in terms of difficulty, assignments, instructors, exams, course content and material etc.`;

    const newSaveEval: StudentAssociatedPastEvaluationItem = {
        uniId: dataStudent?.data.hostUni.id,
        authorId: user?.bilkentId!,
        comment: currentEvaluation || "",
        rate: givenRating || 0,
        evalStatus: "SAVED"
    }

    const newSubmitEval: StudentAssociatedPastEvaluationItem = {
        uniId: dataStudent?.data.hostUni.id,
        authorId: user?.bilkentId!,
        comment: currentEvaluation || "",
        rate: givenRating || 0,
        evalStatus: "SUBMITTED"
    }
    const saveEval = () => {
        mutateSaveEvalUni(newSaveEval);
    }
    const submitEval = () => {
        mutateSubmitEvalUni(newSubmitEval);
    }
    if (isCoursesLoading || isEvalLoading || isStudentLoading) {
        return (
            <LoadingPage />
        )
    }

    if (isCoursesError || isStudentError) {
        return (
            <ErrorPage />
        )
    }

    // Generate courses for the Select. Select requires the use of a field called value and label.
    const availableCourses: Array<HostCourse> = courses!.map((c) => {
        return {
            ...c,
            value: c.courseName,
            label: c.courseName
        }
    });

    const availableCourseNames: Array<string> = availableCourses.map((course) => {
        return course.courseName;
    });
    return (
        <>
            <Select
                searchable
                placeholder="Choose a course to evaluate"
                allowDeselect
                label={"Choose a course to evaluate"}
                nothingFound="No course found"
                onSearchChange={setSearchedCourse}
                onChange={(value) => {
                    setSelectedCourse(value);
                    const id = availableCourses.find(course => course.courseName === value)?.courseUUID!;
                    setSelectedCourseId(id);
                    mutateGetPastCourseEval(selectedCourseId);
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
                    editable={(evaluation?.evalStatus !== "SUBMITTED")}
                    saveEval={saveEval} submitEval={submitEval}></Evaluation>}
        </>
    );
}

export default EvaluateCoursesPage;