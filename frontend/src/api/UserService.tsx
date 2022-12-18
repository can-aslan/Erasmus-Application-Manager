import { AxiosInstance } from "axios"
import useAxiosSecure from "../hooks/useAxiosSecure"
import { NewStaff, User } from "../types"
import { ResponseUser } from "../types/responseTypes"
import axios from "./axios"

export const login = async (bilkentId: string, password: string) => {
    // const axiosSecure = useAxiosSecure()
    const response = await axios.post<ResponseUser>('/auth/login', 
        JSON.stringify({bilkentId, password}),
        {
            headers: {'Content-Type': 'application/json'},
            withCredentials: true
        })
        console.log("response:", response);
    return response.data

}

export const logout = async (userUuid: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post('/auth/logout', JSON.stringify({userUuid}))
    return response.data
}

export const registerUser = async (axios: AxiosInstance, newUser: NewStaff) => {
    const response = await axios.post<NewStaff>('/auth/register', newUser)
    return response.data
}

export const registerStaff = async (axios: AxiosInstance, newUser: NewStaff) => {
    const response = await axios.post<NewStaff>('auth/register/staff', newUser)
    return response.data
}

export const registerInstructor = async (axios: AxiosInstance, newUser: NewStaff) => {
    const response = await axios.post<NewStaff>('auth/register/instructor', newUser)
    return response.data
}