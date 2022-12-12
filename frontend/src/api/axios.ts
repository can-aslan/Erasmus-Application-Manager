import axios from "axios";

export default axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL,
    withCredentials: true
})

export const axiosSecure = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL,
    headers: {'Content-Type': 'application/json'},
    withCredentials: true,
})