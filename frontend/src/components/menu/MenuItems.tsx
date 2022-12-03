import { Button, Menu } from "@mantine/core";
import { MenuItem } from "../../types";

interface MenuItemsInterface {
    items: Array<MenuItem>,
}

const MenuItems = ({items}: MenuItemsInterface) => {
    const menuItems = items.map((item) => {
        return (
            <Menu.Item  
                key={item.to}
                color={item.color}
                icon={item.icon}
                onClick={item.action}
                href={item.to}
                component="a"
            >
                {item.label}
            </Menu.Item>
        )
    })

    return (
        <div>
            {menuItems}
        </div>
    );
}
 
export default MenuItems;