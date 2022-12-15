import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"

import { Course, CourseRequest, CourseWishlistItem, HostCourse, } from "../../types"
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

export const getUniCoursesByUserId = async (axios: AxiosInstance, userId: string) => {
    const response = await axios.get<ResponseHostUniCourses>(`/course/host/user/${userId}`)
    return response.data
}

export const getCourseWishlist = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponseStudentCourseWishlist>(`/student/courseWishlist/${studentId}`)
    return response.data
}

export const saveWishlist = async (axios: AxiosInstance, studentId: string, wishlistItems: CourseWishlistItem[] | undefined) => {
    const response = await axios.post(`/student/${studentId}/courseWishlist/save`,
        JSON.stringify({
            data: {
                wishlistItems
            }
        })    
    )
    return response.data
}

export const submitWishlist = async (axios: AxiosInstance, studentId: string, wishlistItems: CourseWishlistItem[] | undefined) => {
    const response = await axios.post(`/student/${studentId}/courseWishlist/submit`,
        JSON.stringify({
            data: {
                wishlistItems
            }
        })    
    )
    return response.data
}
    
export const getPreviouslyRequestedCourses = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponsePreviousCourseRequests>(`/api/student/previousCourseRequests/${studentId}`)
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
