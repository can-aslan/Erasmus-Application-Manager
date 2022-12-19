import { AxiosInstance } from "axios"
import { Student } from "../types"
import { ResponseStudent } from "../types/responseTypes"

export const getStudent = async (axios: AxiosInstance, bilkentId: string) => {
    const response = await axios.get<ResponseStudent>(`student/${bilkentId}`)
    return response.data
}

export const getAllStudents = async (axios: AxiosInstance) => {
    const response = await axios.get<Array<Student>>('student')
    return response.data
}