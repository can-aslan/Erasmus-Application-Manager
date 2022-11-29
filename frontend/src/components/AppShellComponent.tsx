import { AppShell } from "@mantine/core";
import {
	BrowserRouter as Router,
	Link,
	Route,
	Routes
} from 'react-router-dom';
import { User, UserEnum } from "../types";
import AppShellHeader from "./navbar/AppShellHeader";
import OutgoingStudentNavbar from "./navbar/OutgoingStudentNavbar";

const AppShellComponent = () => {
	// TODO: Add a hook that will choose the navbar to be generated based on the user type.
	// TODO: This hook will require a Context and a Provider because we will need the User object
	// which should at least include the name, email, user type, JWT "access" token.
	const userType = UserEnum.OutgoingStudent
	const mockUser: User = {email: 'sss@gmail.com', name: 'Selim', userType}

	return (  
		<Router>
			<AppShell
				padding="md"
				navbar={<OutgoingStudentNavbar user={mockUser}/>}
				header={<AppShellHeader />}
				styles={(theme) => ({
					main: { backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0] },
				})}
			>
				<Routes>
					<Route path="/universities" element={<div>hey</div>}/>
				</Routes>
			</AppShell>
		</Router>
);
}
 
export default AppShellComponent;