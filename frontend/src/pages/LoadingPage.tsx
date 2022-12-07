import { Center, Loader, Stack, Text } from "@mantine/core";

interface LoadingPageProps {
    message?: string,
}

const LoadingPage = ({message}: LoadingPageProps) => {
    return (
        <Center sx={{height: '100vh'}}>
            <Stack align='center'>
                <Loader size={60}/>
                {message || <Text size={22} color='blue'>Please wait while we get things ready for you!</Text>}
            </Stack>
        </Center>
    );
}
 
export default LoadingPage;