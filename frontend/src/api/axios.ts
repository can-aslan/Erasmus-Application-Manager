import axios from "axios";

export default axios.create({
    baseURL: import.meta.env.BACKEND_URL
})

export const axiosSecure = axios.create({
    baseURL: import.meta.env.BACKEND_URL,
    headers: {'Content-Type': 'application/json'},
    withCredentials: true,
})