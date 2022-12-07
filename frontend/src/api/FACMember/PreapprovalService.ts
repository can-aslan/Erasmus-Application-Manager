import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"

const axiosSecure = useAxiosSecure()

export const getSubmittedPreApprovals = async (facMemberId: string) => {
    const response = await axiosSecure.get<Array<PreApprovalForm>>(`/api/fac-member/submittedPreApprovals/${facMemberId}`)
    return response.data
}