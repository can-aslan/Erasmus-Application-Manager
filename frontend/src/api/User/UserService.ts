import useAxiosSecure from "../../hooks/useAxiosSecure"
import { User } from "../../types"
import { ResponseUser } from "../../types/responseTypes"
import axios from "../axios"

export const login = async (bilkentId: string, password: string) => {
    // const axiosSecure = useAxiosSecure()
    const response = await axios.post<ResponseUser>('/api/v1/auth/login', 
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
    const response = await axiosSecure.post('/api/auth/logout', JSON.stringify({userUuid}))
    return response
}