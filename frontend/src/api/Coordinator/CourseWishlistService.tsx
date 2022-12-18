import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { CoordinatorWishlistChange, Course, StudentAssociatedCourse } from "../../types"
import { ResponseAllStudentCourseWishlist, ResponseStudentCourseWishlist, ResponseStudentSpecificCourseWishlist } from "../../types/responseTypes"


export const getAllStudentWishlists = async (axios: AxiosInstance, coordinatorId: string) => {
    const response = await axios.get<ResponseAllStudentCourseWishlist>(`/coordinator/${coordinatorId}/wishlist`)
    return response.data
}

export const getStudentWishlist = async (axios: AxiosInstance, coordinatorId: string, studentBilkentId:string) => {
    const response = await axios.get<ResponseStudentCourseWishlist>(`/coordinator/${coordinatorId}/wishlist/student/${studentBilkentId}`)
    return response.data
}

export const changeStatus = async (axios: AxiosInstance, body: CoordinatorWishlistChange) => {
    const response = await axios.post<ResponseAllStudentCourseWishlist>(`/coordinator/wishlist/determineStatus`, body)
    return response.data
}