import { Navbar } from "@mantine/core";
import { NavbarLink } from "../../types";
import LinkButton from "../buttons/LinkButton";

interface NavbarLinksProps {
    links: Array<NavbarLink>
}

const NavbarLinks = ({links}: NavbarLinksProps) => {
    const navbarLinks = links.map((link) => {
        return (
            <LinkButton link={link} />
        )
    })
    
    return (
        <div>
            {navbarLinks}
        </div>
    );
}
 
export default NavbarLinks;