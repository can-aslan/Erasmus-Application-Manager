import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"


export const getSubmittedPreApprovals = async (facMemberId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<Array<PreApprovalForm>>(`/api/fac-member/submittedPreApprovals/${facMemberId}`)
    return response.data
}

export const approveSubmittedPreApproval = async (facMemberId: string, studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<PreApprovalForm>(`/api/fac-member/approvePreApproval`)
    return response.data
    
}