import { ActionIcon, useMantineColorScheme } from "@mantine/core";
import { IconMoon, IconSun } from '@tabler/icons';

const ToggleThemeIcon = () => {
    const { colorScheme, toggleColorScheme } = useMantineColorScheme()
    const isThemeDark = colorScheme === 'dark'
    
    return (
        <ActionIcon
            variant="outline"
            color={isThemeDark ? 'yellow' : 'blue'}
            onClick={() => toggleColorScheme()}
            title="Toggle color scheme"
        >
            {isThemeDark ? <IconSun size={18} /> : <IconMoon size={18}/>}
        </ActionIcon>
    );
}
 
export default ToggleThemeIcon;