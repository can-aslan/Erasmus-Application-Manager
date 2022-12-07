import { Center, Stack, Text } from "@mantine/core";

interface ErrorPageProps {
    message?: string,
}

const ErrorPage = ({message}: ErrorPageProps) => {
    return (
    <Center sx={{height: '100vh'}}>
        <Stack align='center'>
            {message || <Text size={24} color='red'>Something went wrong!</Text>}
        </Stack>
    </Center>
    );
}
 
export default ErrorPage;