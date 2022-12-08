import { Button, Group, PasswordInput, Stack, TextInput, TypographyStylesProvider } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useHotkeys } from "@mantine/hooks";
import { useMutation } from "@tanstack/react-query";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';
import { login } from "../../api/User/UserService";
import { useUser } from "../../provider/UserProvider";

interface MutationFunctionProps {
    bilkentID: string,
    pwd: string,
}

const LoginForm = () => {
    const navigate = useNavigate()
    const { setUser } = useUser()
    const loginMutation = useMutation({
        mutationFn: ({bilkentID, pwd}: MutationFunctionProps) => {
            return login(bilkentID, pwd)
        },
        onSuccess: (data) => {
            setUser(data.data);
            toast.success("You've successfully logged in.", {
                position: toast.POSITION.BOTTOM_LEFT
            })
            navigate('/')
        },
        onError: (error) => {
            if (axios.isAxiosError(error)) {
                // TODO: Send error notification
                if (!error.status) {
                    toast.error("Something went wrong with the servers. Please wait while we identify the issue!", {
                        position: toast.POSITION.BOTTOM_LEFT,
                    })
                }
                else if (error.status === 401) {
                    toast.error("You are not authorized.")
                }
            }
        }
    })
    
    useHotkeys([['Enter', () => handleSignIn()]]) // Enter is mapped as shortcut sign in
    
    const form = useForm({
        initialValues: {
            bilkentID: '',
            password: ''
        },
        validate: {
            bilkentID: (value) => value.length > 0 ? null: "ID cannot be empty.",
            password: (value) => value.length > 0 ? null : "Password cannot be empty."
        }
    })
    const handleSignIn = async () => {
        const validate = form.validate()
        if (!validate.hasErrors) {
            const bilkentID = form.values.bilkentID
            const pwd = form.values.password
            loginMutation.mutate({bilkentID, pwd})
        }
    }

    return (
        <form>
            <Stack spacing={16}>
                <TextInput 
                    label="Bilkent ID"
                    placeholder="Your ID"
                    {...form.getInputProps('bilkentID')}
                />
                <PasswordInput
                    label="Password"
                    placeholder="Your password"
                    {...form.getInputProps('password')}
                >
                </PasswordInput>
                <Button 
                    onClick={handleSignIn}
                    loaderPosition='left'
                    loading={loginMutation.isLoading}
                    disabled={loginMutation.isLoading}
                >
                        Sign In
                </Button>
                <Group position="center">
                    <Link 
                        to='/forgot-password'
                    >
                        Forgot your password?
                    </Link>
                </Group>
            </Stack>
        </form>
    );
}
 
export default LoginForm;