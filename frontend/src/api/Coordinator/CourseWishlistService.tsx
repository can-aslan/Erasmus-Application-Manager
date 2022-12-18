import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, StudentAssociatedCourse } from "../../types"
import { ResponseAllStudentCourseWishlist, ResponseStudentCourseWishlist, ResponseStudentSpecificCourseWishlist } from "../../types/responseTypes"


export const getAllStudentWishlists = async (axios: AxiosInstance, coordinatorId: string) => {
    const response = await axios.get<ResponseAllStudentCourseWishlist>(`/coordinator/${coordinatorId}/wishlist`)
    return response.data
}

export const getStudentWishlist = async (axios: AxiosInstance, coordinatorId: string, studentBilkentId:string) => {
    const response = await axios.get<ResponseStudentCourseWishlist>(`/coordinator/${coordinatorId}/wishlist/student/${studentBilkentId}`)
    return response.data
}

export const approveWishlist = async (axios: AxiosInstance, wishlistId: string, coordinatorId: string) => {
    const response = await axios.post<ResponseAllStudentCourseWishlist>(`/coordinator/approveWishlist/`, {
        coordinatorId,
        wishlistId,
    })
    return response.data
}

export const rejectWishlist = async (axios: AxiosInstance, wishlistId: string, coordinatorId: string) => {
    const response = await axios.post<ResponseAllStudentCourseWishlist>(`/coordinator/rejectWishlist/`, {
        coordinatorId,
        wishlistId,
    })
    return response.data
}