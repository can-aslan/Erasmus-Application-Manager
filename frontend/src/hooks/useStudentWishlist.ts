import { useQuery } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { getCourseWishlist } from "../api/Student/CourseService"
import { useUser } from "../provider/UserProvider"
import { ExistingCourseWishlist } from "../types"

export const useStudentWishlist = (axios: AxiosInstance, id: string) => {
    return useQuery({
        queryKey: ['wishlist'],
        queryFn: () => getCourseWishlist(axios, id),
        initialData: undefined,
    })
}
