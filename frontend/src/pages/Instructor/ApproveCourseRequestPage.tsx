import { Center, Box, Stack, Title, Grid, Card, Text, Container, Flex, Button, TextInput, SimpleGrid } from "@mantine/core";
import { useState } from "react";
import ApproveRequestedCoursesGrid from "../../components/grids/ApproveRequestedCoursesGrid";
import { Course, CourseRequest } from "../../types";


const ApproveCourseRequestPage = () => {
    const [hostName, setHostName] = useState("Host name from Api");
    const [waitingCourses, setWaitingCourses] = useState<Array<CourseRequest>>([
    { courseCode: "CS-XXX", courseName: "xxx course", correspondingCourseInBilkent: "CS-201", courseWebPage: "somewebpage.com/cs-201", syllabusLink: "somewebpage.com/syllabus/cs-201" },
    { courseCode: "CS-YYY", courseName: "yyy course", correspondingCourseInBilkent: "CS-202", courseWebPage: "somewebpage.com/cs-202", syllabusLink: "somewebpage.com/syllabus/cs-202" },
    { courseCode: "CS-ZZZ", courseName: "zzz course", correspondingCourseInBilkent: "CS-315", courseWebPage: "somewebpage.com/cs-315", syllabusLink: "somewebpage.com/syllabus/cs-315" },
    { courseCode: "CS-AAA", courseName: "aaa course", correspondingCourseInBilkent: "CS-115", courseWebPage: "somewebpage.com/cs-115", syllabusLink: "somewebpage.com/syllabus/cs-115" }
    ]);
    return (<Center sx={{ height: '100vh' }}>
        <Box
            sx={{ minWidth: 300 }}
            mx="auto"
        >
            <Stack
                spacing='xl'
            >
                <ApproveRequestedCoursesGrid hostName = {hostName} waitingCourses={waitingCourses} />
            </Stack>
        </Box>
    </Center>);
}

export default ApproveCourseRequestPage;