import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"

const axiosSecure = useAxiosSecure()

export const getPreApprovalFileWithStatus = async (studentId: string) => {
    const response = axiosSecure.get<PreApprovalForm>(`/api/student/preApproval/${studentId}`)
    return response
}

export const submitPreApprovalFile = async (studentId: string) => {
    const response = axiosSecure.post(`/api/student/preApproval/${studentId}`)
    return response
}

// TODO: Mutation: generate & download pre approval file
export const generateAndDownloadPreApproval = async (studentId: string) => {
    const response = axiosSecure.post(`/api/student/preApproval/generate/download/${studentId}`)
    return response
}

// TODO: Mutation: generate & submit pre approval file
export const generateAndSubmitPreApproval = async (studentId: string) => {
    const response = axiosSecure.post(`/api/student/preApproval/generate/submit/${studentId}`)
    return response
}