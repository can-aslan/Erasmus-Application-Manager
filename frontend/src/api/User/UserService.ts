import useAxiosSecure from "../../hooks/useAxiosSecure"
import { User } from "../../types"
import axios from "../axios"


// TODO: Mutate: Create access and refresh tokens on the server side.
export const login = async (bilkentId: string, password: string) => {
    // const axiosSecure = useAxiosSecure()
    const response = await axios.post<Record<"data", User>>('/api/v1/auth/login', 
        JSON.stringify({bilkentId, password}),
        {
            headers: {'Content-Type': 'application/json'},
            withCredentials: true
        })
        console.log("response:", response);
    return response.data

}

// TODO: Mutate: Delete access and refresh tokens on the server side.
export const logout = async (userUuid: string) => {
    const axiosSecure = useAxiosSecure()
    const response = await axiosSecure.post('/api/auth/logout', JSON.stringify({userUuid}))
    return response
}