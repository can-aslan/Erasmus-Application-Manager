import { Anchor, Button, Group, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useNavigate } from "react-router-dom";
import { useUserDispatch } from "../../provider/UserProvider";
import { User, UserEnum } from "../../types";

const LoginForm = () => {
    const navigate = useNavigate()
    const dispatch = useUserDispatch()
    
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

    const onClick = () => {
        const validate = form.validate()
        if (!validate.hasErrors) {
            // TODO: Make a request to backend, replace the mock data with data coming from backend
            // Handle invalid login case.
            // TODO: Set loading state for the button.
            const userCredentials: User = {
                name: "hey",
                email: "hey@email.com",
                userType: UserEnum.OutgoingStudent,
                accessToken: "token123"
            }
            dispatch({
                type: 'LOGIN',
                payload: userCredentials,
            })

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
                    onClick={onClick}
                    loaderPosition='left'
                >
                        Sign In
                </Button>
                <Group position="center">
                    {/* TODO: A new page for forgot password? Or just change the form? */}
                    <Anchor>Forgot your password?</Anchor>
                </Group>
            </Stack>
        </form>
    );
}
 
export default LoginForm;