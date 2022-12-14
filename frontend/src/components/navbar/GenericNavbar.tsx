import { Divider, Navbar } from "@mantine/core";
import { IconLogout } from '@tabler/icons';
import { useNavigate } from "react-router-dom";
import useLogout from "../../hooks/useLogout";
import { MenuItem, NavbarLink, User } from "../../types";
import UserButton from "../buttons/UserButton";
import GenericMenu from "../menu/GenericMenu";
import NavbarLinks from "./NavbarLinks";

interface GenericNavbarProps {
    user: User,
    navlinks: Array<NavbarLink>
}

const GenericNavbar = ({user, navlinks}: GenericNavbarProps) => {
    const navigate = useNavigate()
    const logout = useLogout()

    const onSignOut = () => {
        logout()
        navigate('/login')
    }
    const USER_BUTTON_MENU_ITEMS: Array<MenuItem> = [
        {
            icon: <IconLogout size={18}/>, 
            label: 'Sign Out', 
            color: 'red', 
            bgColor: 'red',
            action: onSignOut
        }
    ]
    
    return (
        <Navbar 
            styles={(theme) => ({
                main: { backgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[8] : theme.colors.gray[0] },
            })} 
            width={{ base: 275 }} 
            height="100"
            p="xs"
        >
            <Navbar.Section grow>
                <NavbarLinks links={navlinks}></NavbarLinks>
            </Navbar.Section>
            <Divider my={'sm'} />
                <GenericMenu 
                    menuLabel="Options" 
                    menuItems={USER_BUTTON_MENU_ITEMS} 
                    target={<UserButton 
                                email={user.email} 
                                name={user.name} 
                                userType={user.userType}
                            />
                    }
                />
            <Navbar.Section>
            </Navbar.Section>
        </Navbar>
    );
}
 
export default GenericNavbar;