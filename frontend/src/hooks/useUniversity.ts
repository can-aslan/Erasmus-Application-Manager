import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getUniversity } from "../api/Student/UniversityService"

export const useUniversity = (axios: AxiosInstance, universityId: string) => {
    return useQuery({
        queryKey: ['university'],
        queryFn: () => getUniversity(axios, universityId),
    })
}