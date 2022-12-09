import axios from "../api/axios";
import { useUser } from "../provider/UserProvider";

const useLogout = () => {
    const { setUser } = useUser()
    
    const logout = async () => {
        setUser(null)
        try {
            const response = await axios('/api/auth/logout', {
                withCredentials: true,
            })
        }
        catch (err) {
            console.log(err)
        }
    }

    return logout
}
 
export default useLogout;