import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"
import { ResponsePreApprovalForm, ResponsePreApprovalFormList } from "../../types/responseTypes"


export const getSubmittedPreApprovals = async (facMemberId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponsePreApprovalFormList>(`/api/fac-member/submittedPreApprovals/${facMemberId}`)
    return response.data
}

export const approveSubmittedPreApproval = async (facMemberId: string, studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<ResponsePreApprovalForm>(`/api/fac-member/approvePreApproval`)
    return response.data
    
}