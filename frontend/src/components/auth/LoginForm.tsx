import { Button, Group, PasswordInput, Stack, TextInput, TypographyStylesProvider } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useHotkeys } from "@mantine/hooks";
import { useMutation } from "@tanstack/react-query";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { toast } from 'react-toastify';
import { login } from "../../api/User/UserService";
import { useUser } from "../../provider/UserProvider";
import { UserEnum } from "../../types";

interface MutationFunctionProps {
    bilkentID: string,
    pwd: string,
}

const LoginForm = () => {
    const navigate = useNavigate()
    const { setUser } = useUser()
    const loginMutation = useMutation({
        mutationFn: ({ bilkentID, pwd }: MutationFunctionProps) => {
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
                if (!error.status) {
                    toast.error("We can't reach BEAM servers at the moment. Please wait while we identify the issue!", {
                        position: toast.POSITION.BOTTOM_LEFT,
                    })
                }
                else if (error.status === 401) {
                    toast.error("You are not authorized.")
                }
                else if (error.status === 403) {
                    toast.error("Either bilkent ID or password is wrong")
                }
                else {
                    toast.error("Something went wrong!")
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
            bilkentID: (value) => value.length > 0 ? null : "ID cannot be empty.",
            password: (value) => value.length > 0 ? null : "Password cannot be empty."
        }
    })
    const handleSignIn = async () => {
        const validate = form.validate()
        if (!validate.hasErrors) {
            const bilkentID = form.values.bilkentID
            const pwd = form.values.password
            // FIXME: Commented until database is connected
            //loginMutation.mutate({bilkentID, pwd})


            setUser({
                refreshToken: "refreshT",
                accessToken: "accessT",
                email: "selim.guler@ug.bilkent.edu.tr",
                id: "22002811",
                name: "Selim Can",
                surname: "Güler",
                userType: UserEnum.OutgoingStudent,
            })
            navigate("/")

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