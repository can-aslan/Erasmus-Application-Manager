import { Button, Flex, Select, Tabs, Text, TextInput } from "@mantine/core";
import { useForm } from "@mantine/form";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { toast } from "react-toastify";
import RegisterInstructorTab from "../../components/auth/register/RegisterInstructorTab";
import RegisterStaffTab from "../../components/auth/register/RegisterStaffTab";
import RegisterUserTab from "../../components/auth/register/RegisterUserTab";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { NewUser, UserEnum } from "../../types";

const RegisterPage = () => {
    return (
        <Tabs color='blue' defaultValue='register-user'>
            <Tabs.List>
                <Tabs.Tab value="user">User</Tabs.Tab>
                <Tabs.Tab value="staff">Staff</Tabs.Tab>
                <Tabs.Tab value="instructor">Instructor</Tabs.Tab>
            </Tabs.List>
            <Tabs.Panel value="user">
                <RegisterUserTab />
            </Tabs.Panel>
            <Tabs.Panel value="staff">
                <RegisterStaffTab />
            </Tabs.Panel>
            <Tabs.Panel value="instructor">
                <RegisterInstructorTab />
            </Tabs.Panel>
        </Tabs>

    );
}
 
export default RegisterPage;