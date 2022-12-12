import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getCourses } from "../api/Student/CourseService"

export const useCourses = (axios: AxiosInstance) => {
    return useQuery({
        queryKey: ['courses'],
        queryFn: () => getCourses(axios),
    })
}