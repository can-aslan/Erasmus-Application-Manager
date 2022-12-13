import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, CourseRequest, PreviousCourseRequest, StudentAssociatedWishlist } from "../../types"
import { ResponseCourse, ResponseCourseList, ResponseCourseRequest, ResponsePreviousCourseRequests, ResponseUniSpecificCourses, ResponseStudentSpecificCourseWishlist } from "../../types/responseTypes"

export const getCourses = async (axios: AxiosInstance) => {
    const response = await axios.get<ResponseCourseList>(`/student/courses`)
    return response.data
}

export const getCourse = async (axios: AxiosInstance, courseId: string) => {
    const response = await axios.get<ResponseCourse>(`/student/courses/${courseId}`)
    return response.data
}

export const getUniCourses = async (axios: AxiosInstance, uniId: string) => {
    const response = await axios.get<ResponseUniSpecificCourses>(`/uni/${uniId}/courses`)
    return response.data
}

export const getCourseWishlist = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponseStudentSpecificCourseWishlist>(`/student/courseWishlist/${studentId}`)
    return response.data
}

export const saveWishlist = async (axios: AxiosInstance, wishlist: StudentAssociatedWishlist | undefined) => {
    const response = await axios.post<ResponseStudentSpecificCourseWishlist>(`/student/courseWishlist/${wishlist?.studentId}`,
        JSON.stringify({
            data: {
                ...wishlist
            }
        })    
    )
    return response.data
}
    
export const getPreviouslyRequestedCourses = async (axios: AxiosInstance, studentId: string) => {
    const response = await axios.get<ResponsePreviousCourseRequests>(`/api/student/previousCourseRequests/${studentId}`)
    return response.data
}

export const makeCourseRequest = async (axios: AxiosInstance, course: CourseRequest, studentId: string) => {
    const response = await axios.post<ResponseCourseRequest>(`/api/student/courseRequest/${studentId}`,
        JSON.stringify({
            ...course,
        }
    ))
    return response.data
}
