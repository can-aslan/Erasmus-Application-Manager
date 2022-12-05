import useAxiosSecure from "../../hooks/useAxiosSecure"
import { User } from "../../types"
import axios from "../axios"

const axiosSecure = useAxiosSecure()

// TODO: Mutate
export const login = async (bilkentId: string, pwd: string) => {
    const response = await axios.post<User>('/api/auth/login', 
        JSON.stringify({bilkentId, pwd}),
        {
            headers: {'Content-Type': 'application/json'},
            withCredentials: true
        })
    return response

}

// TODO: Mutate
export const logout = async (uuid: string) => {
    const response = await axiosSecure.post('/api/auth/logout', JSON.stringify({uuid}))
    return response
}