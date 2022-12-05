import { Button, Stack, Text, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useState } from 'react';

const ForgotPasswordForm = () => {
    const [feedbackMsg, setFeedbackMsg] = useState<string>('')

    const form = useForm({
        initialValues: {
            bilkentID: '',
            email: '',
        },

        validate: {
            bilkentID: (value) => value.length > 0 ? null : 'Bilkent ID field cannot be empty',
            email: (value) => (/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/).test(value) ? null : 'Invalid email',
        }
    })
    
    const onClick = () => {
        // TODO: Send request to backend
        const validation = form.validate()
        if (validation.hasErrors) {

            setFeedbackMsg('')
        }
        else {
            setFeedbackMsg('We have sent you an email with the instructions to reset your password\n if the bilkent ID and email you provided are valid.')
        }
    }

    return (
        <Stack spacing={16}>
            <TextInput 
                label='Bilkent ID'
                placeholder="Your Bilkent ID"
                {...form.getInputProps('bilkentID')}

            />
            <TextInput
                label='E-mail'    
                placeholder="Your email"
                {...form.getInputProps('email')}
            />
            <Button
                onClick={onClick}
            >
                Send Email
            </Button>
            {feedbackMsg && 
                <Text sx={{wordWrap: 'break-word'}} color='red'>
                    {feedbackMsg}
                </Text>
            }
        </Stack>
    );
}
 
export default ForgotPasswordForm;