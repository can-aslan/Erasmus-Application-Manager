import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getFile } from "../api/FileService"
import { useUser } from "../provider/UserProvider"

export const usePreApprovalStatus = (axios: AxiosInstance, userId: string) => {
    return useQuery({
        queryKey: ['preApprovalForm'],
        queryFn: () => getFile(axios, userId),
    })
}