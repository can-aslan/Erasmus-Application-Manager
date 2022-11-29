import { Navbar } from "@mantine/core";
import { NavLink } from "react-router-dom";
import { NavbarLink } from "../../types";
import LinkButton from "../buttons/LinkButton";

interface NavbarLinksProps {
    links: Array<NavbarLink>
}

const defaultStyle = {
    textDecoration: 'none',
}

const activeLinkStyle = {
    textDecoration: 'underline',
    color: '#1aac83',
}

const NavbarLinks = ({links}: NavbarLinksProps) => {
    const navbarLinks = links.map((link) => {
        return (
            <NavLink 
                end 
                to={link.to}
                style={({ isActive }) => isActive ? activeLinkStyle : defaultStyle}
            >
                <LinkButton link={link} />
            </NavLink>
        )
    })
    
    return (
        <div>
            {navbarLinks}
        </div>
    );
}
 
export default NavbarLinks;