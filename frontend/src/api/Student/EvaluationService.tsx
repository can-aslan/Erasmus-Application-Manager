import { AxiosInstance } from "axios"
import { StudentAssociatedCoursePastEvaluationItem, StudentAssociatedUniPastEvaluationItem } from "../../types"
import { ResponseStudentPastEvaluation } from "../../types/responseTypes"

export const getStudentPastUniEval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponseStudentPastEvaluation>(`eval/student/${studentId}/university`)
    return response.data
}

export const getStudentPastCourseEval = async (axios: AxiosInstance, studentId: string, courseId: string) => {
    const response = await axios.get<ResponseStudentPastEvaluation>(`eval/student/${studentId}/course/${courseId}`)
    return response.data
}

export const evaluateUni = async (axios: AxiosInstance, evalForm: StudentAssociatedUniPastEvaluationItem) => {
    const response = await axios.post<StudentAssociatedUniPastEvaluationItem>('/eval/university',
        evalForm)
    return response.data
}

export const evaluateCourse = async (axios: AxiosInstance, evalForm: StudentAssociatedUniPastEvaluationItem) => {
    const response = await axios.post<StudentAssociatedCoursePastEvaluationItem>('/eval/course',
        evalForm)
    return response.data
}