import { Button, Flex, Textarea, TextInput, Title } from "@mantine/core";
import { IconSchool } from "@tabler/icons";
import { useMutation, useQuery } from "@tanstack/react-query";
import { SetStateAction, useState } from "react";
import { toast } from "react-toastify";
import { evaluateUni, getStudentPastUniEval } from "../../api/Student/EvaluationService";
import { getStudent } from "../../api/StudentService";
import Evaluation from "../../components/evaluation/Evaluation";
import RatingBar from "../../components/rating/RatingBar";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useUser } from "../../provider/UserProvider";
import { StudentAssociatedPastEvaluationItem } from "../../types";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";


const EvaluateUniversityPage = () => {
    const axiosSecure = useAxiosSecure()
    const { user } = useUser()
    const { data: dataEval, isError: isEvalError, isLoading: isEvalLoading } = useQuery({
        queryFn: () => getStudentPastUniEval(axiosSecure, user?.bilkentId!),
        queryKey: ["get_past_eval"]
    })

    const { data: dataStudent, isError: isStudentError, isLoading: isStudentLoading } = useQuery({
        queryFn: () => getStudent(axiosSecure, user?.bilkentId!),
        queryKey: ["get_student"]
    })
    const evaluation = dataEval?.data;
    const [currentEvaluation, setCurrentEvaluation] = useState(evaluation?.comment);
    const [givenRating, setGivenRating] = useState(evaluation?.rate);
    const uniName = dataStudent?.data.hostUni.name;
    const description = `You can evaluate ${uniName} in terms of food, dormitories, social opportunities, people, campus facilities etc.`;
    const { mutate: mutateSaveEvalUni, data: userSaveData, isLoading: isSaveUniLoading } = useMutation({
        mutationKey: ['saveUniEval'],
        mutationFn: (newEval: StudentAssociatedPastEvaluationItem) => evaluateUni(axiosSecure, newEval),
        onSuccess: () => toast.success(`Evaluation saved.`),
        onError: () => toast.error("Evaluation save failed.")
    })

    const { mutate: mutateSubmitEvalUni, data: userSubmitData, isLoading: isSubmitUniLoading } = useMutation({
        mutationKey: ['submitUniEval'],
        mutationFn: (newEval: StudentAssociatedPastEvaluationItem) => evaluateUni(axiosSecure, newEval),
        onSuccess: () => toast.success(`Evaluation submitted.`),
        onError: () => toast.error("Evaluation submit failed.")
    })
    
    if (isEvalLoading || isStudentLoading) {
        return (
            <LoadingPage />
        )
    }

    if (isEvalError || isStudentError) {
        return (
            <ErrorPage />
        )
    }

    const newSaveEval: StudentAssociatedPastEvaluationItem = {
        uni_id: dataStudent?.data.hostUni.id,
        author_id: user?.bilkentId!,
        comment: currentEvaluation || "",
        rate: givenRating || 0,
        eval_status: "SAVED"
    }

    const newSubmitEval: StudentAssociatedPastEvaluationItem = {
        uni_id: dataStudent?.data.hostUni.id,
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

    return (
        <Evaluation
            saveEval={saveEval}
            submitEval={submitEval}
            editable={(evaluation?.eval_status !== "SUBMITTED")}
            emptySymbol={<IconSchool />}
            fullSymbol={<IconSchool color={"#1971c2"} />}
            givenRating={givenRating ?? 0}
            evaluationName={uniName ?? ""}
            currentEvaluation={currentEvaluation ?? ""}
            setGivenRating={setGivenRating}
            setCurrentEvaluation={setCurrentEvaluation}
            description={description}></Evaluation>
    );
}

export default EvaluateUniversityPage;