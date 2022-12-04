import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useUser } from "../../provider/UserProvider";
import { User, UserEnum } from "../../types";

interface RequireAuthProps {
    allowedUsers: Array<UserEnum>
}

const RequireAuth = ({ allowedUsers }: RequireAuthProps) => {
    const { user } = useUser()
    const location = useLocation()

    const isUser = (x: any): x is User => Object.keys(x).length === 0

    // User did not login at all
    if (!isUser(user)) {
        return <Navigate to='/login' state={{ from: location }} replace/>
    }
    else {
        if (!allowedUsers.includes(user?.userType)) {
            return <Navigate to='/unauthorized' state={{ from: location }} replace/>
        }
    }
        
    return (
        <Outlet />
    );
}
 
export default RequireAuth;