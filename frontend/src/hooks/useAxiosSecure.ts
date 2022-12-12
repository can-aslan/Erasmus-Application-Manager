import { useEffect } from "react";
import { axiosSecure } from "../api/axios";
import { useUser } from "../provider/UserProvider";
import { User } from "../types";
import useRefreshToken from "./useRefreshToken";

const useAxiosSecure = (contentType?: string) => {
    const refresh = useRefreshToken()
    const { user } = useUser()

    useEffect(() => {
        const requestIntercept = axiosSecure.interceptors.request.use(config => {
                if (!config?.headers!['Authorization']) {
                    config.headers!['Authorization'] = `Bearer ${(user as User).accessToken}`
                }

                return config
            }, (error) => Promise.reject(error)
        )

        const responseIntercept = axiosSecure.interceptors.response.use(
            response => response,
            async (error) => {
                const prevRequest = error?.config
                if (error?.response?.status === 403 && !prevRequest?.sent) {
                    prevRequest.sent = true
                    const newAccessToken = await refresh()
                    prevRequest.headers['Authorization'] = `Bearer ${newAccessToken}`
                    return axiosSecure(prevRequest)
                }
                return Promise.reject(error)
            }
        )

        return () => {
            axiosSecure.interceptors.request.eject(requestIntercept)
            axiosSecure.interceptors.response.eject(responseIntercept)

        }
    }, [user, refresh])

    return axiosSecure
}
 
export default useAxiosSecure;