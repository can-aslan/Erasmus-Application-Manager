import { Anchor, Button, Group, PasswordInput, Stack, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useHotkeys } from "@mantine/hooks";
import { useNavigate } from "react-router-dom";
import { useUser } from "../../provider/UserProvider";
import { User, UserEnum } from "../../types";

const LoginForm = () => {
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

    const handleSignIn = () => {
        const validate = form.validate()
        if (!validate.hasErrors) {
            // TODO: Make a request to backend, replace the mock data with data coming from backend
            // Handle invalid login case.
            // TODO: Set loading state for the button.
            const userCredentials: User = {
                name: "hey",
                email: "hey@email.com",
                userType: UserEnum.OutgoingStudent,
                accessToken: "token123",
                refreshToken: 'hello',
            }
            setUser(userCredentials)
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