import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getUniversities } from "../api/Student/UniversityService"

export const useUniversities = (axios: AxiosInstance) => {
    return useQuery({
        queryKey: ['universities'],
        queryFn: () => getUniversities(axios)
    })
}