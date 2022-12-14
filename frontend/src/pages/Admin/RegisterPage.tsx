import { Button, Flex, Select, Text, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { toast } from "react-toastify";
import { register } from "../../api/UserService";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { NewUser, UserEnum } from "../../types";

const RegisterPage = () => {
    const axiosSecure = useAxiosSecure()
    const [userType, setUserType] = useState<UserEnum>(UserEnum.OutgoingStudent)
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
                userType
            }
            mutateRegister(user)
        }
    }

    const { mutate: mutateRegister, data: userData, isLoading: isRegisterLoading } = useMutation({
        mutationKey: ['registerUser'],
        mutationFn: (user: NewUser) => register(axiosSecure, user),
        onSuccess: () => toast.success(`Registered the user.`),
        onError: () => toast.success("Student registration failed.")
    })


    return (
        <Flex gap='xl' direction='column'>
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
            <Select
                label="User Type"
                placeholder="Choose student type"
                value={userType}
                onChange={setUserType}
                data={[
                    ...Object.values(UserEnum)
                ]}
                {...form.getInputProps('userType')}
            />
            <Button
                onClick={handleRegister}
                loading={isRegisterLoading}
            >
                Register
            </Button>
            {
                userData &&
                    <Text color='red'>
                        User details:
                        Bilkent ID: {userData.bilkentId}
                        Password: {userData.password}
                    </Text>
            }
        </Flex>

    );
}
 
export default RegisterPage;