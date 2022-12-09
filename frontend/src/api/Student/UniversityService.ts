import useAxiosSecure from "../../hooks/useAxiosSecure"
import { ResponseUniversities, ResponseUniversity } from "../../types/responseTypes"

export const getUniversities = async () => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseUniversities>(`/api/student/universities`)
    return response.data
}

export const getUniversity = async (universityId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseUniversity>(`/api/student/universities/${universityId}`)
    return response.data
}