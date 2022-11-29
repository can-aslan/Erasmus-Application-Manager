import { Menu } from "@mantine/core";
import { MenuItem } from "../../types";
import MenuItems from "./MenuItems";

interface UserOptionsMenuProps {
    menuItems: Array<MenuItem>,
    menuLabel?: string,
    target: React.ReactNode,
}

const GenericMenu = ({target, menuItems, menuLabel}: UserOptionsMenuProps) => {
    return (
        <Menu withArrow>
            <Menu.Target>
                {target}
            </Menu.Target>
            <Menu.Dropdown>
                {menuLabel && <Menu.Label>{menuLabel}</Menu.Label>}
                <MenuItems
                    items={menuItems}
                ></MenuItems>
            </Menu.Dropdown>
        </Menu>
    );
}
 
export default GenericMenu;