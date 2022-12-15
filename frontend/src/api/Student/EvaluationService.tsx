import { AxiosInstance } from "axios"
import { StudentAssociatedPastEvaluationItem } from "../../types"
import { ResponseStudentPastEvaluation } from "../../types/responseTypes"

export const getStudentPastUniEval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponseStudentPastEvaluation>(`student/${studentId}/university`)
    return response.data
}

export const evaluateUni = async (axios: AxiosInstance, evalForm: StudentAssociatedPastEvaluationItem) => {
    const response = await axios.post<StudentAssociatedPastEvaluationItem>('/eval/university', evalForm)
    return response.data
}