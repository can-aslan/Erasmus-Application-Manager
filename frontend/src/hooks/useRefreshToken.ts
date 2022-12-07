import axios, { axiosSecure } from "../api/axios";
import { useUser } from "../provider/UserProvider";
import { User } from "../types";

const useRefreshToken = () => {
    const { setUser, user } = useUser()

    const refresh = async () => {
        const response = await axiosSecure.get('/api/v1/auth/refresh', {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${user?.refreshToken}`
            }
        })

        setUser((prev) => {
            return {
                ...prev!,
                userType: response.data.userType,
                accessToken: response.data.accessToken
            }
        })

        return response.data.accessToken
    }

    return refresh
}
 
export default useRefreshToken;