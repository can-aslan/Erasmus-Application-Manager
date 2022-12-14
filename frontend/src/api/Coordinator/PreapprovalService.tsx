import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"
import { ResponsePreApprovalForm, ResponsePreApprovalFormList } from "../../types/responseTypes"


export const getSubmittedPreApprovalsCoord = async (axios: AxiosInstance, coordinatorId: string) => {
    const response = await axios.get<ResponsePreApprovalFormList>(`/coordinator/submittedPreApprovals/${coordinatorId}`)
    return response.data
}

export const approveSubmittedPreApprovalCoord = async (axios: AxiosInstance, coordinatorId: string, studentId: string) => {
    const response = await axios.post<ResponsePreApprovalForm>(`/coordinator/approvePreApproval`)
    return response.data
}