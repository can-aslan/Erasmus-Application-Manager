import Unauthorized from "../../pages/UnauthorizedPage";
import { useUser } from "../../provider/UserProvider";
import { User } from "../../types";
import { NAVBAR_LINK_OBJECTS } from "../../utils/constants";
import GenericNavbar from "./GenericNavbar";

const AppNavbar = () => {
    const { user } = useUser()
    console.log(user)

    if (user) {
        const navlinks = NAVBAR_LINK_OBJECTS[user.userType]
        return <GenericNavbar user={user} navlinks={navlinks}/>
    }
    
    return (
        <>
        {console.log("why tho")}
            <Unauthorized />
        </>
    );
}
 
export default AppNavbar;