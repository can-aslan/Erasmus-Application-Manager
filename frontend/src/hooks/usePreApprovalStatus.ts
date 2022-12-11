import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getPreApprovalFile } from "../api/Student/PreapprovalService"
import { useUser } from "../provider/UserProvider"

export const usePreApprovalStatus = (axios: AxiosInstance, userId: string) => {
    return useQuery({
        queryKey: ['preApprovalForm'],
        queryFn: () => getPreApprovalFile(axios, userId),
    })
}