import { axiosSecure } from "../api/axios";
import { useUser } from "../provider/UserProvider";

const useRefreshToken = () => {
    const { setUser, user } = useUser()
    
    const refresh = async () => {
        console.log(user)
        console.log(`Bearer ${user.refreshToken}`)
        const response = await axiosSecure.get('/auth/refresh', {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${user.refreshToken}`
            }
        })

        setUser({
            ...user,
            accessToken: response.data.data.accessToken,
        })
        return response.data.data.accessToken
    }

    return refresh
}
 
export default useRefreshToken;