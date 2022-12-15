import { Button, Flex, LoadingOverlay, Select, TextInput } from "@mantine/core"
import { useForm } from "@mantine/form"
import { useMutation } from "@tanstack/react-query"
import { useState } from "react"
import { toast } from "react-toastify"
import { registerStaff } from "../../../api/UserService"
import useAxiosSecure from "../../../hooks/useAxiosSecure"
import { useCourses } from "../../../hooks/useCourses"
import { BilkentCourse, NewUser } from "../../../types"
import { DEPARTMENTS, FACULTIES } from "../../../utils/constants"

const RegisterInstructorTab = () => {
    const axiosSecure = useAxiosSecure()
    const [department, setDepartment] = useState('')
    const [faculty, setFaculty] = useState('')
    const [courses, setCourses] = useState<Array<BilkentCourse>>()

    // const { data: bilkentCourses, isLoading: isCoursesLoading, isError: isCoursesError} = useCourses(axiosSecure)

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
        if (!validation.hasErrors && department && faculty) {
            const user: NewUser = {
                ...form.values,
                department,
                faculty
            }
            mutateRegister(user)
        }
    }

    const { mutate: mutateRegister, data: userData, isLoading: isRegisterLoading } = useMutation({
        mutationKey: ['registerUser'],
        mutationFn: (user: NewUser) => registerStaff(axiosSecure, user),
        onSuccess: () => toast.success(`Registered the user.`),
        onError: () => toast.error("Student registration failed.")
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
            <Select
                label="Select faculty"
                placeholder="Faculties"
                data={FACULTIES}
            />
            <Select
                label="Select department"
                placeholder="Departments"
                data={DEPARTMENTS}
            />
            <Button onClick={handleRegister} loading={isRegisterLoading}>Register</Button>
        </Flex>
    );
}
 
export default RegisterInstructorTab;