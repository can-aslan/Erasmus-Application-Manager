import { AppShell, Header, Navbar } from "@mantine/core";
import { User, UserEnum } from "../../types";
import AppShellHeader from "./AppShellHeader";
import OutgoingStudentNavbar from "./OutgoingStudentNavbar";

const AppShellNavbar = () => {
	// TODO: Add a hook that will choose the navbar to be generated based on the user type.
	// TODO: This hook will require a Context and a Provider because we will need the User object
	// which should at least include the name, email, user type, JWT "access" token.
	const userType = UserEnum.OutgoingStudent
	const mockUser: User = {email: 'sss@gmail.com', name: 'Selim', userType}

	return (  
		<AppShell
			padding="md"
			navbar={<OutgoingStudentNavbar user={mockUser}/>}
			header={<AppShellHeader />}
			styles={(theme) => ({
				main: { backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0] },
		})}
	>
		{/* Your application here */}
	</AppShell>
);
}
 
export default AppShellNavbar;