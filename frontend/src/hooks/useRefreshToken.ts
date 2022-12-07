import axios from "../api/axios";
import { useUser } from "../provider/UserProvider";
import { User } from "../types";

const useRefreshToken = () => {
    const { setUser } = useUser()

    const refresh = async () => {
        const response = await axios.get('/api/auth/refresh', {
            withCredentials: true,
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