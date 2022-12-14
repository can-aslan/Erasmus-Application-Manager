import { AxiosInstance } from "axios"
import { ResponseAllLearningAgreements } from "../../types/responseTypes"

export const generateLearningAgreement = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.post(`/student/learningAgreement/generate/${studentId}`)
    return response.data
}

export const getAllLearningAgreements = async (axios: AxiosInstance, userId: string) => {
    const response = await axios.get<ResponseAllLearningAgreements>(`/coordinator/learningAgreement/fetch/${userId}`)
    return response.data
}