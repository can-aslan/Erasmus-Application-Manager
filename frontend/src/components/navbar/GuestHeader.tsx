import { Box, Flex, Header, Title } from "@mantine/core";
import { NavLink } from "react-router-dom";
import { NavbarLink } from "../../types";
import ToggleThemeIcon from "../ToggleThemeIcon";
import LinkButton from "../buttons/LinkButton";

const defaultStyle = {
    textDecoration: 'none',
}

const activeLinkStyle = {
    textDecoration: 'underline',
    color: '#1aac83',
}

const GuestHeader = () => {
    const links: Array<NavbarLink> = [
        {
            label: 'Login',
            to: '/login',
        },
        {
            label: 'Universities',
            to: '/guest'
        }

    ]

    return (
        <Header
            height={100}
            p="lg"
        >
            <Flex justify='space-between' align='center'>
                <Title color='blue' ml={50}>Beam</Title>
                <Flex gap='xl' align='center'>
                    {links.map(link => {
                        return (
                            <NavLink 
                                end 
                                key={link.to}
                                to={link.to}
                                style={({ isActive }) => isActive ? activeLinkStyle : defaultStyle}
                            >
                                <LinkButton link={link} />
                            </NavLink>
                        )
                    })}
                    <ToggleThemeIcon mr={50} />
                </Flex>
            </Flex>
        </Header>
    );
}
 
export default GuestHeader;