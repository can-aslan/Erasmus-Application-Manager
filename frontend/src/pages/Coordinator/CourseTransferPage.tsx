import { ActionIcon, Autocomplete, Box, Button, Card, Center, Divider, Flex, Grid, Group, Text, TextInput, Title } from "@mantine/core";
import { IconArrowLeftRight, IconCircleX, IconDeviceFloppy, IconPlus } from '@tabler/icons';
import { useState } from "react";
import { CoordinatorAssociatedStudents, Course, HostCourse } from "../../types";

type CoursePair = {
    hostUniCourse: HostCourse,
    bilkentCourse: Course,
}

const CourseTransferPage = () => {
    const isCoursePair = (p: any): p is CoursePair => Object.keys(p).length !== 0
    const [selectedStudent, setSelectedStudent] = useState('')
    const [coursePairs, setCoursePairs] = useState<Array<CoursePair | {}>>([
        {
            bilkentCourse: {
                bilkentCredits: 3,
                ECTSCredits: 5,
                courseCode: "CS 299",
                courseName: "Internship and All",
                uuid: "superlongid",
                elective: false,
            },
             hostUniCourse: {
                courseCode: "CENG 7172",
                courseName: "Internship and Beyond",
                ECTSCredits: 5,
                id: "extralongid",
                grade: "A",
             }
        },
        {},
        {},
        {},
        {},
        {},
        {},
    ])
    const lengthOfCoursePairs = coursePairs.map(p => isCoursePair(p)).length


    // TODO: Replace this variable with database request that will fetch students associated with the current coordinator
    const students: Array<CoordinatorAssociatedStudents> = [
        {
            studentUuid: "student1",
            studentName: "Selim Can Güler",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
        {
            studentUuid: "student1",
            studentName: "Selim Can Güler",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
        {
            studentUuid: "student1",
            studentName: "Selim Can Güler",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
    ]
    // TODO: Fetch pre-approval of selected student
    
    // TODO: Fetch transcript of selected student


    // TODO: Fetch Bilkent courses
    const bilkentCourses: Array<Course> = [
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
    const courses = bilkentCourses.map(c => {
        return {
            ...c,
            value: c.courseName,
        }
    })
    const hostCourses = hostCoursesMock.map(c => {
        return {
            ...c,
            value: c.courseName,
        }
    })

    const data = students.map(s => {
        return {
            ...s,
            value: s.studentName,
        }
    })

    const handleFormTransfer = () => {
        // TODO: useMutation()
    }

    return (
        <Center>
            <Flex maw={1200} direction="column" gap={36}>
                <Autocomplete 
                    label={<Text fw={600} size={18}>Student:</Text>}
                    data={data}
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
                            {coursePairs.map((p, index) => {
                                return (
                                    <>
                                        <Grid.Col span={1}>
                                            <Autocomplete 
                                                data={hostCourses}
                                                placeholder="Course in host university"
                                                value={isCoursePair(p) ? p?.hostUniCourse?.courseName : ''}
                                                onChange={(value) =>  {
                                                    setCoursePairs(coursePairs.map((p, pairIndex) => {
                                                        const selectedHostCourse: HostCourse | undefined = hostCourses.find((c) => c.courseName === value)
                                                        
                                                        if (index === pairIndex) {
                                                            return {
                                                                selectedHostCourse
                                                            }
                                                        }

                                                        return p
                                                    }))

                                                    return value
                                                }}
                                            />
                                        </Grid.Col>
                                        <Grid.Col span={1}>
                                            <Autocomplete 
                                                data={courses}
                                                value={isCoursePair(p) ? p?.hostUniCourse?.courseName : ''}
                                                onChange={(value) =>  {
                                                    setCoursePairs(coursePairs.map((p, pairIndex) => {
                                                        const selectedBilkentCourse: Course | undefined = bilkentCourses.find((c) => c.courseName === value)
                                                        
                                                        if (index === pairIndex) {
                                                            return {
                                                                selectedBilkentCourse
                                                            }
                                                        }

                                                        return p
                                                    }))

                                                    return value
                                                }}
                                                placeholder="Correspondant course in Bilkent"
                                            />
                                        </Grid.Col>
                                    </>
                                )
                            })}
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