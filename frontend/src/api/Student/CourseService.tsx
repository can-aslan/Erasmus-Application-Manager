import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, CourseRequest, PreviousCourseRequest, StudentAssociatedWishlist } from "../../types"
import { ResponseCourse, ResponseCourseList, ResponseCourseRequest, ResponsePreviousCourseRequests, ResponseSchoolSpecificCourses, ResponseStudentSpecificCourseWishlist } from "../../types/responseTypes"



export const getCourses = async () => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseCourseList>(`/student/courses`)
    return response.data
}

export const getCourse = async (courseId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseCourse>(`/student/courses/${courseId}`)
    return response.data
}

export const getSchoolCourses = async (schoolId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseSchoolSpecificCourses>(`/student/school/${schoolId}/courses`)
    return response.data
}

export const getCourseWishlist = async (studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseStudentSpecificCourseWishlist>(`/student/courseWishlist/${studentId}`)
    return response.data
}

export const saveWishlist = async (wishlist: StudentAssociatedWishlist | undefined) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<ResponseStudentSpecificCourseWishlist>(`/student/courseWishlist/${wishlist?.studentId}`,
        JSON.stringify({
            data: {
                ...wishlist
            }
        })    
    )
    return response.data
}
    
export const getPreviouslyRequestedCourses = async (studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponsePreviousCourseRequests>(`/api/student/previousCourseRequests/${studentId}`)
    return response.data
}

export const makeCourseRequest = async (course: CourseRequest, studentId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post<ResponseCourseRequest>(`/api/student/courseRequest/${studentId}`,
        JSON.stringify({
            ...course,
        }
    ))
    return response.data
}
