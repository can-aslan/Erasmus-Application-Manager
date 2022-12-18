import { AxiosInstance } from "axios"

export const placeStudents = async (axios: AxiosInstance, department: string) => {
    const response = await axios.post(`placement/place/${department}`)
    return response.data
}