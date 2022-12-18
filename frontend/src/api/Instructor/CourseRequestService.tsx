import { AxiosInstance } from "axios"
import { InstructorCourseRequestChange } from "../../types"
import { ResponseCourseRequest, ResponsePreviousCourseRequests } from "../../types/responseTypes"

export const getWaitingRequestedCourses = async (axios: AxiosInstance, instructorId: string) => {
    const response = await axios.get<ResponsePreviousCourseRequests>(`/instructor/${instructorId}/course/requested`)
    return response.data
}

export const changeRequestStatus = async (axios: AxiosInstance, requestBody: InstructorCourseRequestChange) => {
    const response = await axios.post<ResponseCourseRequest>(`/instructor/course/determineStatus`, 
        requestBody
    )
    return response.data
}