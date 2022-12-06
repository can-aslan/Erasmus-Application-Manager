import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, CourseRequest, PreviousCourseRequest } from "../../types"

const axiosSecure = useAxiosSecure()


export const getCourses = async () => {
    const response = axiosSecure.get<Array<Course>>(`/api/student/courses`)
    return response
}

export const getCourse = async (courseId: string) => {
    const response = axiosSecure.get<Course>(`/api/student/courses/${courseId}`)
    return response
}

export const getSchoolCourses = async (schoolId: string) => {
    const response = axiosSecure.get<Array<Course>>(`/api/student/school/${schoolId}/courses`)
    return response
}

export const getCourseWishlist = async (studentId: string) => {
    const response = axiosSecure.get<Array<Course>>(`/api/student/courseWishlist/${studentId}`)
    return response
}

export const saveWishlist = async (studentId: string, courses: Array<Course>) => {
    const response = axiosSecure.post<Array<Course>>(`/api/student/courseWishlist`,
        JSON.stringify({
            courses,
            studentId
        })    
    )
    return response
}
    
export const getPreviouslyRequestedCourses = async (studentId: string) => {
    const response = axiosSecure.get<Array<PreviousCourseRequest>>(`/api/student/courseRequest/${studentId}`)
    return response
}

export const makeCourseRequest = async (course: Course, studentId: string) => {
    const response = axiosSecure.post<CourseRequest>(`/api/student/courseRequest`,
        JSON.stringify({
            ...course,
            studentId
        }
    ))
    return response
}
