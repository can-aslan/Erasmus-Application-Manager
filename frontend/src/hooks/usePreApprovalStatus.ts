import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getPreApprovalStatus } from "../api/FileService"

export const usePreApprovalStatus = (axios: AxiosInstance, bilkentId: string) => {
    return useQuery({
        queryKey: ['preApprovalFormStatus'],
        queryFn: () => getPreApprovalStatus(axios, bilkentId),
    })
}