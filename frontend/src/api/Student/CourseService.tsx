import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"

import { Course, CourseRequest, CourseWishlistItem, HostCourse, NewCourseWish, } from "../../types"
import { ResponseBilkentCourse, ResponseBilkentCourseList, ResponseCourseRequest, ResponseHostUniCourses, ResponsePreviousCourseRequests, ResponseStudentCourseWishlist, ResponseStudentSpecificCourseWishlist } from "../../types/responseTypes"

export const getBilkentCourses = async (axios: AxiosInstance) => {
    const response = await axios.get<ResponseBilkentCourseList>(`/course/bilkent`)
    return response.data
}

export const getCourse = async (axios: AxiosInstance, courseId: string) => {
    const response = await axios.get<ResponseBilkentCourse>(`/student/courses/${courseId}`)
    return response.data
}

export const getUniCourses = async (axios: AxiosInstance, uniId: string) => {
    const response = await axios.get<ResponseHostUniCourses>(`/course/host/uni/${uniId}`)
    return response.data
}

export const getUniCoursesByUserId = async (axios: AxiosInstance, bilkentId: string) => {
    const response = await axios.get<ResponseHostUniCourses>(`/student/${bilkentId}/uni/course`)
    return response.data
}

export const getCourseWishlist = async (axios: AxiosInstance, bilkentId: string) => {
    const response = await axios.get<ResponseStudentCourseWishlist | undefined>(`/wishlist/fetch/${bilkentId}`)
    return response.data
}

export const saveWishlist = async (axios: AxiosInstance, bilkentId: string, newWish: NewCourseWish) => {
    const response = await axios.post<Response>(`/wishlist/add/${bilkentId}`, newWish)    
    return response.data
}

export const submitWishlist = async (axios: AxiosInstance, bilkentId: string) => {
    const response = await axios.post(`/wishlist/submit/${bilkentId}`)
    return response.data
}
    
export const deleteWishItem = async (axios: AxiosInstance, bilkentId: string, wishItemId: string) => {
    const response = await axios.delete(`/wishlist/delete/${bilkentId}/${wishItemId}`)
    return response.data
}

export const getPreviouslyRequestedCourses = async (axios: AxiosInstance) => {
    const response = await axios.get<ResponsePreviousCourseRequests>(`/course/student/fetch`)
    return response.data
}

export const makeCourseRequest = async (axios: AxiosInstance, course: CourseRequest) => {
    const response = await axios.post<ResponseCourseRequest>(`/course/student/request`,
        JSON.stringify({
            ...course,
        }
    ))
    return response.data
} 
