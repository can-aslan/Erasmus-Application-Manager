import { AxiosInstance } from "axios"
import { ResponseStudent } from "../types/responseTypes"

export const getStudent = async (axios: AxiosInstance, bilkentId: string) => {
    const response = await axios.get<ResponseStudent>(`student/${bilkentId}`)
    return response.data
}