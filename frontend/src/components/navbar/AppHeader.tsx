import { Group, Header, Title } from "@mantine/core";
import ToggleThemeIcon from "../ToggleThemeIcon";

const AppHeader = () => {
    return (
        <Header
            height={80}
            p="lg"
        >
            <Group position="apart">
                <Title ml={20}>Beam</Title>
                <ToggleThemeIcon mr={50}/>
            </Group>
        </Header>
    );
}
 
export default AppHeader;