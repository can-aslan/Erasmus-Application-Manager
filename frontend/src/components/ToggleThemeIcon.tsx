import { ActionIcon, useMantineColorScheme } from "@mantine/core";
import { IconMoon, IconSun } from '@tabler/icons';

interface ToggleThemeIconProps {
    mr?: number,
}


const ToggleThemeIcon = ({mr}: ToggleThemeIconProps) => {
    const { colorScheme, toggleColorScheme } = useMantineColorScheme()
    const isThemeDark = colorScheme === 'dark'
    
    return (
        <ActionIcon
            variant="outline"
            color={isThemeDark ? 'yellow' : 'blue'}
            onClick={() => toggleColorScheme()}
            title="Toggle color scheme"
            mr={mr}
            size={32}
        >
            {isThemeDark ? <IconSun size={22} /> : <IconMoon size={22}/>}
        </ActionIcon>
    );
}
 
export default ToggleThemeIcon;