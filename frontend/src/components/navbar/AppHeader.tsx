import { Container, Flex, Group, Header, Title } from "@mantine/core";
import { useUser } from "../../provider/UserProvider";
import { UserEnum } from "../../types";
import { PROGRESSBAR_STEP_OBJECTS } from "../../utils/constants";
import ToggleThemeIcon from "../ToggleThemeIcon";
import GenericProgressBar from "./GenericProgressBar";

const AppHeader = () => {
    const { user } = useUser()

    if (user.userType === UserEnum.OutgoingStudent) {
        const progressBarSteps = PROGRESSBAR_STEP_OBJECTS[user.userType]
        return <Header
            height={100}
            p="lg"
        >
            <Flex align='' justify='space-between'>
                <Title ml={20}>Beam</Title>
                <GenericProgressBar active={2} user={user} progressBarSteps={progressBarSteps} />
                <ToggleThemeIcon mr={50}/>
            </Flex>

        </Header>;
    }
    return <Header
        height={80}
        p="lg"
    >
        <Group position="apart">
            <Title color='blue' ml={20}>Beam</Title>
            <ToggleThemeIcon mr={50} />
        </Group>
    </Header>;

}

export default AppHeader;