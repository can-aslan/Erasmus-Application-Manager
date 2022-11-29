import { Group, Header, Title } from "@mantine/core";
import ToggleThemeIcon from "../ToggleThemeIcon";

const AppShellHeader = () => {
    return (
        <Header
            height={80}
            p="lg"
        >
            <Group position="apart">
                <Title ml={20}>Desiderius</Title>
                <ToggleThemeIcon mr={50}/>
            </Group>
        </Header>
    );
}
 
export default AppShellHeader;