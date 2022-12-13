import { Center, Box, Stack, Title, Grid, Card, Text, Container, Flex, Button, TextInput, SimpleGrid } from "@mantine/core";
import { useState } from "react";
import ApproveRequestedCoursesGrid from "../../components/grids/ApproveRequestedCoursesGrid";
import { Course, CourseRequest } from "../../types";


const ApproveCourseRequestPage = () => {
    const [hostName, setHostName] = useState("Host name from Api");
    const [waitingCourses, setWaitingCourses] = useState<Array<CourseRequest>>([
    { requestId: "0", hostCode: "CS-XXX", name: "xxx course", bilkentCode: "CS-201", webpage: "somewebpage.com/cs-201", syllabusLink: "somewebpage.com/syllabus/cs-201" },
    { requestId: "1", hostCode: "CS-YYY", name: "yyy course", bilkentCode: "CS-202", webpage: "somewebpage.com/cs-202", syllabusLink: "somewebpage.com/syllabus/cs-202" },
    { requestId: "2", hostCode: "CS-ZZZ", name: "zzz course", bilkentCode: "CS-315", webpage: "somewebpage.com/cs-315", syllabusLink: "somewebpage.com/syllabus/cs-315" },
    { requestId: "3", hostCode: "CS-AAA", name: "aaa course", bilkentCode: "CS-115", webpage: "somewebpage.com/cs-115", syllabusLink: "somewebpage.com/syllabus/cs-115" }
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