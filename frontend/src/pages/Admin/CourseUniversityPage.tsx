import { Tabs } from "@mantine/core";

const CourseUniversityPage = () => {
    return (
    <Tabs color='blue' defaultValue='register-user'>
        <Tabs.List>
            <Tabs.Tab value="course">Course</Tabs.Tab>
            <Tabs.Tab value="university">University</Tabs.Tab>
        </Tabs.List>
        <Tabs.Panel value="instructor">
        </Tabs.Panel>
    </Tabs>
    );
}
 
export default CourseUniversityPage;