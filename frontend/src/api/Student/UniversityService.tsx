import { AxiosInstance } from "axios"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { ResponseUniversities, ResponseUniversity } from "../../types/responseTypes"

export const getUniversities = async (axios: AxiosInstance) => {
    const response = await axios.get<ResponseUniversities>(`/student/universities`)
    return response.data
}

export const getUniversity = async (axios: AxiosInstance, universityId: string) => {
    const response = await axios.get<ResponseUniversity>(`/student/universities/${universityId}`)
    return response.data
}