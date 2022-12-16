import { Box, Center, Flex, Header, Stack, Title } from "@mantine/core";
import { NavLink } from "react-router-dom";
import ToggleThemeIcon from "../components/ToggleThemeIcon";
import LoginForm from "../components/auth/LoginForm";
import GuestHeader from "../components/navbar/GuestHeader";

const defaultStyle = {
    textDecoration: 'none',
}

const activeLinkStyle = {
    textDecoration: 'underline',
    color: '#1aac83',
}

const LoginPage = () => {

    return (
        <>
            <GuestHeader/>
            <Center sx={{height: '87vh'}}>
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
        </>
    );
}
 
export default LoginPage;