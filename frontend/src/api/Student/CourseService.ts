import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, CourseRequest, PreviousCourseRequest } from "../../types"

const axiosSecure = useAxiosSecure()


export const getCourses = async () => {
    const response = await axiosSecure.get<Array<Course>>(`/api/student/courses`)
    return response.data
}

export const getCourse = async (courseId: string) => {
    const response = await axiosSecure.get<Course>(`/api/student/courses/${courseId}`)
    return response.data
}

export const getSchoolCourses = async (schoolId: string) => {
    const response = await axiosSecure.get<Array<Course>>(`/api/student/school/${schoolId}/courses`)
    return response.data
}

export const getCourseWishlist = async (studentId: string) => {
    const response = await axiosSecure.get<Array<Course>>(`/api/student/courseWishlist/${studentId}`)
    return response.data
}

export const saveWishlist = async (studentId: string, courses: Array<Course>) => {
    const response = await axiosSecure.post<Array<Course>>(`/api/student/courseWishlist/${studentId}`,
        JSON.stringify({
            courses,
        })    
    )
    return response.data
}
    
export const getPreviouslyRequestedCourses = async (studentId: string) => {
    const response = await axiosSecure.get<Array<PreviousCourseRequest>>(`/api/student/previousCourseRequests/${studentId}`)
    return response.data
}

export const makeCourseRequest = async (course: Course, studentId: string) => {
    const response = await axiosSecure.post<CourseRequest>(`/api/student/courseRequest/${studentId}`,
        JSON.stringify({
            ...course,
        }
    ))
    return response.data
}
