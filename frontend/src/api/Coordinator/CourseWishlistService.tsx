import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, StudentAssociatedCourse } from "../../types"
import { ResponseAllStudentCourseWishlist } from "../../types/responseTypes"


export const getAllStudentWishlists = async (coordinatorId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseAllStudentCourseWishlist>(`/coordinator/studentWishlists/${coordinatorId}`)
    return response.data
}

export const approveWishlist = async (wishlistId: string, coordinatorId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<ResponseAllStudentCourseWishlist>(`/coordinator/approveWishlist/`, {
        coordinatorId,
        wishlistId,
    })
    return response.data
}

export const rejectWishlist = async (wishlistId: string, coordinatorId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<ResponseAllStudentCourseWishlist>(`/coordinator/rejectWishlist/`, {
        coordinatorId,
        wishlistId,
    })
    return response.data
}