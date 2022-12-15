import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getBilkentCourses } from "../api/Student/CourseService"

export const useCourses = (axios: AxiosInstance) => {
    return useQuery({
        queryKey: ['courses'],
        queryFn: () => getBilkentCourses(axios),
    })
}