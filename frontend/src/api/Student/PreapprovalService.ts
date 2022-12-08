import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"
import { ResponsePreApprovalForm } from "../../types/responseTypes"

export const getPreApprovalFileWithStatus = async (studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponsePreApprovalForm>(`/api/student/preApproval/${studentId}`)
    return response.data
}

export const submitPreApprovalFile = async (studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post(`/api/student/preApproval/${studentId}`)
    return response.data
}

// TODO: Mutation: generate & download pre approval file
export const generateAndDownloadPreApproval = async (studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post(`/api/student/preApproval/generate/download/${studentId}`)
    return response.data
}

// TODO: Mutation: generate & submit pre approval file
export const generateAndSubmitPreApproval = async (studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post(`/api/student/preApproval/generate/submit/${studentId}`)
    return response.data
}