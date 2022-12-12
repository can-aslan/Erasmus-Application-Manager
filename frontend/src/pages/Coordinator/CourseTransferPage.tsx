import { ActionIcon, Autocomplete, Box, Button, Card, Center, Divider, Flex, Grid, Group, Text, TextInput, Title } from "@mantine/core";
import { IconArrowLeftRight, IconCircleX, IconDeviceFloppy, IconPlus } from '@tabler/icons';
import { useState } from "react";
import { CoordinatorAssociatedStudents, Course, HostCourse } from "../../types";

type CoursePair = {
    hostUniCourse: HostCourse,
    bilkentCourse: Course,
}

const NO_OF_MAX_COURSE_PAIRS = 7

const CourseTransferPage = () => {
    const [selectedStudent, setSelectedStudent] = useState('')


    // {
    //     courseCode: "CENG 7172",
    //     courseName: "Internship and Beyond",
    //     ECTSCredits: 5,
    //     id: "extralongid",
    //     grade: "A",
    //  },
    // TODO: Replace this variable with database request that will fetch students associated with the current coordinator
    const students: Array<CoordinatorAssociatedStudents> = [
        {
            studentUuid: "student1",
            file: "file",
            formUuid: "form1",
            studentName: "Selim Can Güler",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
        {
            studentUuid: "student1",
            file: "file",
            formUuid: "form1",
            studentName: "Selim Can Güler",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
        {
            studentUuid: "student1",
            file: "file",
            formUuid: "form1",
            studentName: "Selim Can Güler",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
    ]

    // TODO: Fetch Bilkent courses
    const mockBilkentCourses: Array<Course> = [
        {
            bilkentCredits: 12,
            ECTSCredits: 16,
            courseCode: "CS 319",
            courseName: "Erasmus Application Management Creation",
            uuid: "course1"
        },
        {
            bilkentCredits: 12,
            ECTSCredits: 16,
            courseCode: "CS 315",
            courseName: "Python Quizes",
            uuid: "course2"
        },
        {
            bilkentCredits: 12,
            ECTSCredits: 16,
            courseCode: "CS 202",
            courseName: "Red-Black Tree Something Something",
            uuid: "course3"
        },
    ]
    // TODO: Fetch host courses
    const hostCoursesMock: Array<HostCourse> = [
        {
            courseCode: "CENG333",
            courseName: "Computer Science but with Fortran",
            ECTSCredits: 22222,
            id: "unique course id",
        },
        {
            courseCode: "POLS-66545",
            courseName: "Deep Learning for POLS Students",
            ECTSCredits: 22,
            id: "unique course id 2",
        },
        {
            courseCode: "CS-242",
            courseName: "Modern Web Development with HTML4 and CSS2",
            ECTSCredits: 2222,
            id: "unique course id 3",
        },
    ]
    const courses = mockBilkentCourses.map(c => {
        return {
            ...c,
            value: c.courseName,
        }
    })
    const hostCoursesData = hostCoursesMock.map(c => {
        return {
            ...c,
            value: c.courseName,
        }
    })

    const studentData = students.map(s => {
        return {
            ...s,
            value: s.studentName,
        }
    })

    const handleFormTransfer = () => {
        // TODO: useMutation()
    }

        // TODO: Fetch pre-approval of selected student
    
    // TODO: Fetch transcript of selected student
    return (
        <Center>
            <Flex maw={1200} direction="column" gap={36}>
                <Autocomplete 
                    label={<Text fw={600} size={18}>Student:</Text>}
                    data={studentData}
                    placeholder="Select a student"
                    onChange={(value) => setSelectedStudent(value)}
                />
                <Card p={24}>
                    <Flex gap={24} align='center' justify='space-evenly'>
                        <Flex align='center' direction='column' gap={24}>
                            <Text>Pre-approval form</Text>
                            <Text color='dimmed'>The pre-approval file of the student will appear here</Text>
                        </Flex>
                        <Divider size='md' orientation="vertical"/>
                        <Flex align='center' direction='column' gap={24}>
                            <Text>Transcript</Text>
                            <Text color='dimmed'>The transcript file of the student will appear here</Text>
                        </Flex>
                    </Flex>
                </Card>
                <Card
                    p={32}
                    shadow="lg"
                    radius="lg"
                    sx={{minHeight: 400}}
                >
                    <Flex gap={36} direction='column' align='center' justify='center'>
                        <Grid
                            columns={2}
                            gutter={36}
                            p={12}
                        >
                            <Grid.Col span={1}>
                                <Text size={22} fw={600} ta='center'>Course in host university</Text>
                            </Grid.Col>
                            <Grid.Col span={1}>
                                <Text size={22} fw={600} ta='center'>Course in Bilkent</Text>
                            </Grid.Col>
                            {/* TODO */}
                        </Grid>
                        <Button 
                            leftIcon={<IconDeviceFloppy />}
                            onClick={handleFormTransfer}
                        >
                            {/* 
                                TODO: Create course transfer request that will be inspected by FAC 
                                They should be able to sign the document
                            */}
                            SUBMIT TRANSFER FORM
                        </Button>
                    </Flex>
                </Card>
            </Flex>
        </Center>
    );
}
 
export default CourseTransferPage;