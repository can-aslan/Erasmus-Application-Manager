import { useQuery } from "@tanstack/react-query"
import { getCourseWishlist } from "../api/Student/CourseService"
import { useUser } from "../provider/UserProvider"

export const useStudentWishlist = (id: string) => {
    return useQuery({
        queryKey: ['wishlist'],
        queryFn: () => getCourseWishlist(id),
    })
}