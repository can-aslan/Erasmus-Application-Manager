import { User, UserEnum } from "../../types";
import OutgoingStudentNavbar from "./OutgoingStudentNavbar";

const AppNavbar = () => {
    const userType = UserEnum.OutgoingStudent
	const mockUser: User = {email: 'sss@gmail.com', name: 'Selim', userType}

    return (
        <>
            <OutgoingStudentNavbar user={mockUser}/>
        </>
    );
}
 
export default AppNavbar;