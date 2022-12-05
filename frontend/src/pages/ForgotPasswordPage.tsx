import { ActionIcon, Card, Center, Stack, Title } from "@mantine/core";
import { IconArrowLeft } from '@tabler/icons';
import { Link } from "react-router-dom";
import ForgotPasswordForm from "../components/auth/ForgotPasswordForm";

const ForgotPasswordPage = () => {
    return (
        <Center sx={{height: '100vh'}}>
            <Card 
                shadow='xl'
                radius='md'
                sx={{width: 400}} 
                mx="auto"
                p={24}
            >
                <Stack spacing='lg'>
                    <Link to='/login'>
                        <ActionIcon size='xl'>
                            <IconArrowLeft size={64}/>
                        </ActionIcon>
                    </Link>
                    <Title align="center" order={2}>Forgot Password</Title>
                    <ForgotPasswordForm />
                </Stack>
            </Card>
        </Center>
    );
}
 
export default ForgotPasswordPage;