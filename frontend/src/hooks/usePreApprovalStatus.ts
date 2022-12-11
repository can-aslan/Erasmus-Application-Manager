import { useQuery } from "@tanstack/react-query"
import { getPreApprovalFileWithStatus } from "../api/Student/PreapprovalService"
import { useUser } from "../provider/UserProvider"

export const usePreApprovalStatus = (userId: string) => {
    return useQuery({
        queryKey: ['preApprovalForm'],
        queryFn: () => getPreApprovalFileWithStatus(userId),
    })
}