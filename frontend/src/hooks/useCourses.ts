import { useQuery, useQueryClient } from "@tanstack/react-query"
import { getCourses } from "../api/Student/CourseService"
import { getPreApprovalFileWithStatus } from "../api/Student/PreapprovalService"
import { useUser } from "../provider/UserProvider"

export const useCourses = () => {
    return useQuery({
        queryKey: ['courses'],
        queryFn: () => getCourses(),
    })
}