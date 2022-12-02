import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useUser } from "../../provider/UserProvider";
import { UserEnum } from "../../types";

interface RequireAuthProps {
    allowedUsers: Array<UserEnum>
}

const RequireAuth = ({ allowedUsers }: RequireAuthProps) => {
    const userVal = useUser()
    const location = useLocation()

    // User did not login at all
    if (!userVal?.user) {
        return <Navigate to='/login' state={{ from: location }} replace/>
    }

    // User is not authorized
    if (!allowedUsers.includes(userVal?.user?.userType)) {
        return <Navigate to='/unauthorized' state={{ from: location }} replace/>
    }
    
    return (
        <Outlet />
    );
}
 
export default RequireAuth;