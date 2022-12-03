import Unauthorized from "../../pages/UnauthorizedPage";
import { useUser } from "../../provider/UserProvider";
import { NAVBAR_LINK_OBJECTS } from "../../utils/constants";
import GenericNavbar from "./GenericNavbar";

const AppNavbar = () => {
    const userContext = useUser()

    if (userContext.user) {
        const user = userContext.user
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