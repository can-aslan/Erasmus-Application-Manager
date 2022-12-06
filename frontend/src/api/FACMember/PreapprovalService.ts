import useAxiosSecure from "../../hooks/useAxiosSecure"
import { PreApprovalForm } from "../../types"

const axiosSecure = useAxiosSecure()

export const getSubmittedPreApprovals = (facMemberId: string) => {
    const response = axiosSecure.get<Array<PreApprovalForm>>(`/api/fac-member/submittedPreApprovals/${facMemberId}`)
    return response
}