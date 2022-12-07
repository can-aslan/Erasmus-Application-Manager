import { Center, Stack, Text } from "@mantine/core";

interface ErrorPageProps {
    message?: string,
}

const ErrorPage = ({message}: ErrorPageProps) => {
    return (
    <Center sx={{height: '100vh'}}>
        <Stack align='center'>
            <Text size={24} color='red'>{message || "Couldn't fetch relevant data."}</Text>
        </Stack>
    </Center>
    );
}
 
export default ErrorPage;