import { useQuery } from "@tanstack/react-query"
import { getUniversity } from "../api/Student/UniversityService"

export const useUniversity = (universityId: string) => {
    return useQuery({
        queryKey: ['university'],
        queryFn: () => getUniversity(universityId),
    })
}