import { Navbar } from "@mantine/core";
import { IconLogout, IconSchool } from '@tabler/icons';
import { MenuItem, NavbarLink, User } from "../../types";
import UserButton from "../buttons/UserButton";
import GenericMenu from "../menu/GenericMenu";
import NavbarLinks from "./NavbarLinks";

interface OutgoingStudentNavbarProps {
    user: User
}

const OutgoingStudentNavbar = ({user}: OutgoingStudentNavbarProps) => {
    const MENU_ITEMS: Array<MenuItem> = [
        {icon: <IconLogout size={18}/>, label: 'Sign Out', color: 'red', bgColor: 'red' }
    ]
    const NAVBAR_LINKS: Array<NavbarLink> = [
        {label: 'Universities', to:'/universities', icon: <IconSchool />}
    ]
    
    return (
        <Navbar 
            styles={(theme) => ({
                main: { backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0] },
            })} 
            width={{ base: 250 }} 
            height="100vh"
            p="xs"
        >
            <Navbar.Section>
                <NavbarLinks links={NAVBAR_LINKS}></NavbarLinks>
            </Navbar.Section>
            <Navbar.Section>
                <GenericMenu menuLabel="Options" menuItems={MENU_ITEMS} target={<UserButton email={user.email} name={user.name} userType={user.userType}/>}/>
            </Navbar.Section>
        </Navbar>
    );
}
 
export default OutgoingStudentNavbar;