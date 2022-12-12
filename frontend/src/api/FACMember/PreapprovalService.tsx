import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"
import { ResponsePreApprovalForm, ResponsePreApprovalFormList } from "../../types/responseTypes"


export const getSubmittedPreApprovalsFAC = async (axios: AxiosInstance, facMemberId: string) => {
    const response = await axios.get<ResponsePreApprovalFormList>(`/fac-member/submittedPreApprovals/${facMemberId}`)
    return response.data
}

export const approveSubmittedPreApprovalFAC = async (axios: AxiosInstance, facMemberId: string, studentId: string) => {
    const response = await axios.post<ResponsePreApprovalForm>(`/fac-member/approvePreApproval`)
    return response.data
}