import { useQuery } from "@tanstack/react-query"
import { getPreApprovalFileWithStatus } from "../api/Student/PreapprovalService"
import { useUser } from "../provider/UserProvider"

export const usePreApprovalStatus = () => {
    const { user } = useUser()
    return useQuery({
        queryKey: ['preApprovalForm'],
        queryFn: () => getPreApprovalFileWithStatus(user!.id),
    })
}