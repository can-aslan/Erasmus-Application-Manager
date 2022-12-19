import { Box, Card, Center, Flex, Header, Stack, Title } from "@mantine/core";
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
                <Card 
                    withBorder
                    radius='xl'
                    shadow='xl'
                    p={48}
                    sx={{minWidth: 350}} 
                    mx="auto"
                >
                    <Stack 
                        spacing={'xl'}
                    >
                        <Title color='blue' size='36px'>Welcome to Beam</Title>
                        <LoginForm />
                    </Stack>
                </Card>
            </Center>   
        </>
    );
}
 
export default LoginPage;