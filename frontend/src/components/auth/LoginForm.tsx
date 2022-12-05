import { Button, Group, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useHotkeys } from "@mantine/hooks";
import { AxiosError, isAxiosError } from "axios";
import { useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import axios from "../../api/axios";
import { login } from "../../api/User/UserService";
import { useUser } from "../../provider/UserProvider";
import { User, UserEnum } from "../../types";

const LoginForm = () => {
    const [isLoading, setIsLoading] = useState(false)
    const [error, setError] = useState('')
    const navigate = useNavigate()
    const { setUser } = useUser()
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
        setIsLoading(true)
        const validate = form.validate()
        if (!validate.hasErrors) {
            try {
                // const response = await login(form.values.bilkentID, form.values.password)
                // if (!response) {
                //     setError('No server response.')
                // }
                // else if (response.status === 400) {
                //     setError('Username or password is incorrect.')
                // }
                // else if (response.status === 401) {
                //     setError('Unauthorized.')
                // }
                // else {
                //     setUser(response.data)
                //     navigate('/')
                // }

                const mockUser: User = {
                    uuid: 'heyo',
                    accessToken: 'hey',
                    refreshToken: 'ho',
                    email: 'hey@email.com',
                    name: 'heyo',
                    userType: UserEnum.FACMember,
                }
                setUser(mockUser)
                navigate('/')
            } catch (err) {
                setError('Login unsuccessful')
            }
            finally {
                setIsLoading(false)
            }
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
                    loading={isLoading}
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