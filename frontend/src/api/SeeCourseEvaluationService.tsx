import { AxiosInstance } from "axios"
import { ResponseEvaluation } from "../types/responseTypes"


export const getPastCourseEvaluations = async (axios: AxiosInstance, courseID: string) => {
    const response = await axios.get<ResponseEvaluation>(`/eval/course/${courseID}`)
    return response.data
}