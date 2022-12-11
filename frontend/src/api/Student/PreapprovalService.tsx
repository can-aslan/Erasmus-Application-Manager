import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { ResponsePreApprovalForm } from "../../types/responseTypes"

export const getPreApprovalFile = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponsePreApprovalForm>(`/student/preApproval/${studentId}`)
    return response.data
}

export const submitPreApprovalFile = async (axios: AxiosInstance, formData: FormData, studentId: string) => {
    const response = await axios.post(`/student/uploadPreApproval/${studentId}`, {
        formData
    })
    return response.data
}

// TODO: Mutation: generate & download pre approval file
export const generateAndDownloadPreApproval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.post(`/student/preApproval/generate/download/${studentId}`)
    return response.data
}

// TODO: Mutation: generate & submit pre approval file
export const generateAndSubmitPreApproval = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.post(`/student/preApproval/generate/submit/${studentId}`)
    return response.data
}