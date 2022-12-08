import { useQuery } from "@tanstack/react-query"
import { getUniversities } from "../api/Student/UniversityService"

export const useUniversities = () => {
    return useQuery({
        queryKey: ['universities'],
        queryFn: getUniversities
    })
}