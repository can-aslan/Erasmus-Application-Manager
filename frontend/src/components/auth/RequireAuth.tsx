import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useUser } from "../../provider/UserProvider";
import { UserEnum } from "../../types";

interface RequireAuthProps {
    allowedUsers: Array<UserEnum>
}

const RequireAuth = ({ allowedUsers }: RequireAuthProps) => {
    const { user } = useUser()
    const location = useLocation()

    // User did not login at all
    if (!user) {
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