import { axiosSecure } from "../api/axios";
import { useUser } from "../provider/UserProvider";

const useRefreshToken = () => {
    const { setUser, user } = useUser()

    const refresh = async () => {
        console.log(user)
        console.log(`Bearer ${user?.refreshToken}`)
        const response = await axiosSecure.get('/api/v1/auth/refresh', {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${user?.refreshToken}`
            }
        })

        setUser(response.data)
        return response.data.accessToken
    }

    return refresh
}
 
export default useRefreshToken;