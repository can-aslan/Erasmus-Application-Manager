import Unauthorized from "../../pages/Feedback/UnauthorizedPage";
import { useUser } from "../../provider/UserProvider";
import { User } from "../../types";
import { NAVBAR_LINK_OBJECTS } from "../../utils/constants";
import GenericNavbar from "./GenericNavbar";

const AppNavbar = () => {
    const { user } = useUser()

    if (user) {
        const navlinks = NAVBAR_LINK_OBJECTS[user.userType]
        return <GenericNavbar user={user} navlinks={navlinks}/>
    }
    
    return (
        <>
            <Unauthorized />
        </>
    );
}
 
export default AppNavbar;