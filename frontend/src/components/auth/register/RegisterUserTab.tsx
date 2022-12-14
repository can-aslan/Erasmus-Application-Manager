import { Button, Flex, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-toastify";
import { registerUser } from "../../../api/UserService";
import useAxiosSecure from "../../../hooks/useAxiosSecure";
import { NewUser } from "../../../types";

const RegisterUserTab = () => {
    const axiosSecure = useAxiosSecure()
    const form = useForm({
        initialValues: {
            name: '',
            surname: '',
            email: '',
            bilkentId: '',
        },
        validate: {
            name: (value) => value.length > 0 ? null : "Name cannot be empty.",
            surname: (value) => value.length > 0 ? null : "Surname cannot be empty.",
            email: (value) => value.length > 0 ? null : "Email cannot be empty.",
            bilkentId: (value) => value.length > 0 ? null : "ID cannot be empty.",
        }
    })

    const handleRegister = () => {
        const validation = form.validate()
        if (!validation.hasErrors) {
            const user: NewUser = {
                ...form.values,
            }
            mutateRegister(user)
        }
    }

    const { mutate: mutateRegister, data: userData, isLoading: isRegisterLoading } = useMutation({
        mutationKey: ['registerUser'],
        mutationFn: (user: NewUser) => registerUser(axiosSecure, user),
        onSuccess: () => toast.success(`Registered the user.`),
        onError: () => toast.success("Student registration failed.")
    })
    
    return (
        <Flex
            direction='column' 
            gap='xl'
        >
            <TextInput
                label="Name"
                {...form.getInputProps('name')}
            />
            <TextInput 
                label="Surname"
                {...form.getInputProps('surname')}
            />
            <TextInput 
                label="E-mail"
                {...form.getInputProps('email')}
            />
            <TextInput 
                label="BilkentID"
                {...form.getInputProps('bilkentId')}
            />
            <Button onClick={handleRegister} loading={isRegisterLoading}>Register</Button>
        </Flex>
    );
}
 
export default RegisterUserTab;