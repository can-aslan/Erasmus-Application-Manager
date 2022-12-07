import { Button, Group, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useHotkeys } from "@mantine/hooks";
import { Mutation, useMutation } from "@tanstack/react-query";
import axios from "axios";
import { useEffect, useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import { login } from "../../api/User/UserService";
import { useUser } from "../../provider/UserProvider";
import { User, UserEnum } from "../../types";

interface MutationFunctionProps {
    bilkentID: string,
    pwd: string,
}

const LoginForm = () => {
    const navigate = useNavigate()
    const [error, setError] = useState('')
    const { setUser } = useUser()
    const mutation = useMutation({
        mutationFn: ({bilkentID, pwd}: MutationFunctionProps) => {
            return login(bilkentID, pwd);
        },
        onSuccess: (data) => {
            navigate('/')
        },
        onError: (error) => {
            if (axios.isAxiosError(error)) {
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
            mutation.mutate({bilkentID, pwd})

            const mockUser: User = {
                uuid: 'heyo',
                accessToken: 'hey',
                refreshToken: 'ho',
                email: 'hey@email.com',
                name: 'heyo',
                userType: UserEnum.OutgoingStudent,
            }
            setUser(mockUser)
            navigate('/')
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
                    loading={mutation.isLoading}
                    disabled={mutation.isLoading}
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