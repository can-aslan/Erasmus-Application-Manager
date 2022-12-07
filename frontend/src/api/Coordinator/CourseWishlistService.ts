import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, StudentAssociatedCourse } from "../../types"


export const getAllStudentWishlists = async (coordinatorId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<Array<StudentAssociatedCourse>>(`/api/coordinator/studentWishlists/${coordinatorId}`)
    return response.data
}

export const approveWishlist = async (wishlistId: string, coordinatorId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<Array<StudentAssociatedCourse>>(`/api/coordinator/approveWishlist/`, {
        coordinatorId,
        wishlistId,
    })
    return response.data
}

export const rejectWishlist = async (wishlistId: string, coordinatorId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<Array<StudentAssociatedCourse>>(`/api/coordinator/rejectWishlist/`, {
        coordinatorId,
        wishlistId,
    })
    return response.data
}