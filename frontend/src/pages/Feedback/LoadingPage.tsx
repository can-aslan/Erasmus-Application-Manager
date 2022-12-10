import { Center, Stack, Text } from "@mantine/core";
import ScreenLoadingLottie from "../../components/Loader/ScreenLoadingLottie";

interface LoadingPageProps {
    message?: string,
}

const LoadingPage = ({message}: LoadingPageProps) => {
    return (
        <Center sx={{height: '87vh'}}>
            <Stack align='center'>
                <ScreenLoadingLottie/>
                <Text size={22} color='blue'> {message || "Please wait while we get things ready for you!"}</Text>
            </Stack>
        </Center>
    );
}
 
export default LoadingPage;