import useAxiosSecure from "../../hooks/useAxiosSecure"
import { ResponseUniversities, ResponseUniversity } from "../../types/responseTypes"

export const getUniversities = async () => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseUniversities>(`/student/universities`)
    return response.data
}

export const getUniversity = async (universityId: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.get<ResponseUniversity>(`/student/universities/${universityId}`)
    return response.data
}