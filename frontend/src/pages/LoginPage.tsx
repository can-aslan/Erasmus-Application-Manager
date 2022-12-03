import { Box, Center, Stack, TextInput, Title } from "@mantine/core";
import LoginForm from "../components/auth/LoginForm";
import { useUser, useUserDispatch } from "../provider/UserProvider";

const LoginPage = () => {
    return (
        <Center sx={{height: '100vh'}}>
            <Box 
                sx={{minWidth: 300}} 
                mx="auto"
            >
                <Stack 
                    spacing='xl'
                >
                    <Title color='blue' size='36px'>Welcome to Beam</Title>
                    <LoginForm />
                </Stack>
            </Box>
        </Center>   
    );
}
 
export default LoginPage;