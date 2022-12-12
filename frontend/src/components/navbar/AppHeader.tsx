import { Group, Header, Title } from "@mantine/core";
import ToggleThemeIcon from "../ToggleThemeIcon";
import ProgressBar from "./ProgressBar";

const AppHeader = () => {
    return (
        <Header
            height={80}
            p="lg"
        >
            <Group position="apart">
                <Title ml={20}>Beam</Title>
                <ProgressBar/>
                <ToggleThemeIcon mr={50}/>
            </Group>
        </Header>
    );
}
 
export default AppHeader;