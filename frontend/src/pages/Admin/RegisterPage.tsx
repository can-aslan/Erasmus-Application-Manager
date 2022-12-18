import { Tabs } from "@mantine/core";
import RegisterCoordinator from "../../components/auth/register/RegisterCoordinator";
import RegisterFACMember from "../../components/auth/register/RegisterFACMember";
import RegisterISOStaff from "../../components/auth/register/RegisterISOStaff";
import RegisterInstructorTab from "../../components/auth/register/RegisterInstructorTab";


const RegisterPage = () => {
    return (
        <Tabs color='blue' defaultValue='register-user'>
            <Tabs.List>
                <Tabs.Tab value="instructor">Instructor</Tabs.Tab>
                <Tabs.Tab value="coordinator">Coordinator</Tabs.Tab>
                <Tabs.Tab value="fac-member">FAC Member</Tabs.Tab>
                <Tabs.Tab value="iso-staff">ISO Staff</Tabs.Tab>
            </Tabs.List>
            <Tabs.Panel value="instructor">
                <RegisterInstructorTab />
            </Tabs.Panel>
            <Tabs.Panel value="coordinator">
                <RegisterCoordinator />
            </Tabs.Panel>
            <Tabs.Panel value="fac-member">
                <RegisterFACMember />
            </Tabs.Panel>
            <Tabs.Panel value="iso-staff">
                <RegisterISOStaff />
            </Tabs.Panel>
        </Tabs>

    );
}
 
export default RegisterPage;