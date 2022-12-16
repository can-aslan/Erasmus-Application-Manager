import { ActionIcon, Autocomplete, Box, Button, Card, Center, Divider, Flex, Grid, Group, Text, TextInput, Title } from "@mantine/core";
import { IconArrowLeftRight, IconCircleX, IconDeviceFloppy, IconPlus } from '@tabler/icons';
import React, { useEffect, useState } from "react";
import CourseTable from "../../components/tables/CourseTable";
import { BilkentCourse, CoordinatorAssociatedStudents, Course, CourseWishlistItem, HostCourse } from "../../types";

type CourseTableCourses = {
    bilkentCourse: BilkentCourse
    hostCourse: HostCourse
}

const NO_OF_MAX_COURSE_PAIRS = 7

const CourseTransferPage = () => {
    const [selectedStudent, setSelectedStudent] = useState('')
    const [selectedBilkentCourse, setSelectedBilkentCourse] = useState('')
    const [selectedHostCourse, setSelectedHostCourse] = useState('')
    const [error, setError] = useState(false)
    const [tableItems, setTableItems] = useState<Array<CourseTableCourses>>([])
    // {
    //     courseCode: "CENG 7172",
    //     courseName: "Internship and Beyond",
    //     ECTSCredits: 5,
    //     id: "extralongid",
    //     grade: "A",
    //  },

    // TODO: Fetch pre-approval and transcript of selected student
    useEffect(() => {

    }, [selectedStudent])
    

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

    // TODO: Fetch Bilkent courses
    const mockBilkentCourses: Array<BilkentCourse> = [
        {
            bilkentCredits: 12,
            ects: 16,
            courseCode: "CS 319",
            courseName: "Erasmus Application Management Creation",
            courseUUID: "course1",
            department: 'CS',
        },
        {
            bilkentCredits: 12,
            ects: 16,
            courseCode: "CS 315",
            courseName: "Python Quizes",
            courseUUID: "course2",
            department: 'CS',
        },
        {
            bilkentCredits: 12,
            ects: 16,
            courseCode: "CS 202",
            courseName: "Red-Black Tree Something Something",
            courseUUID: "course3",
            department: 'CS',
        },
    ]
    // TODO: Fetch host courses
    const hostCoursesMock: Array<HostCourse> = [
        {
            courseCode: "CENG333",
            courseName: "Computer Science but with Fortran",
            ects: 22222,
            courseUUID: "unique course id",
            department: 'CS',
        },
        {
            courseCode: "POLS-66545",
            courseName: "Deep Learning for POLS Students",
            ects: 22,
            courseUUID: "unique course id 2",
            department: 'CS',
        },
        {
            courseCode: "CS-242",
            courseName: "Modern Web Development with HTML4 and CSS2",
            ects: 2222,
            courseUUID: "unique course id 3",
            department: 'CS',
        },
    ]
    const bilkentCoursesData = mockBilkentCourses.map(c => c.courseName)
    const hostCoursesData = hostCoursesMock.map(c => c.courseName)

    const studentData = students.map(s => {
        return {
            ...s,
            value: s.studentName,
        }
    })

    const handleFormTransfer = () => {                         
        // TODO: Create course transfer request that will be inspected by FAC 
        // They should be able to sign the document
   
        // TODO: useMutation()
    }

    const handleAddTransfer = () => {
        setError(false)
        if (!selectedBilkentCourse || !selectedHostCourse ) {
            setError(true)
            return
        }
        
        setTableItems((prev) => {
            const bilkentCourse = mockBilkentCourses.find((b) => b.courseName === selectedBilkentCourse)!
            const hostCourse = hostCoursesMock.find((h) => h.courseName === selectedHostCourse)!

            const courseTableCourses: CourseTableCourses = {
                bilkentCourse,
                hostCourse
            }

            return [
                ...prev,
                courseTableCourses
            ]
        })
    }

    const handleRemoveTransfer = (e: React.MouseEvent, bilkentCourseUUID: string ) => {
        setTableItems((prev) => prev.filter(p => p.bilkentCourse.courseUUID !== bilkentCourseUUID))
    }

    
    return (
        <Center>
            <Flex maw={1368} direction="column" gap={36}>
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
                        <Flex direction='column' gap="xl">
                            <Title order={1} color='blue' mb={12}>Add course transfer</Title>
                            <Autocomplete 
                                data={hostCoursesData}
                                label='Host course'
                                value={selectedHostCourse}
                                onChange={setSelectedHostCourse}
                                placeholder="Host course taken by the student"
                                error={error}
                            />
                            <Autocomplete
                                data={bilkentCoursesData}
                                label='Corresponding Bilkent course'
                                value={selectedBilkentCourse}
                                onChange={setSelectedBilkentCourse}
                                placeholder="Corresponding course in Bilkent University"
                                error={error}
                            />
                            <Button 
                                leftIcon={<IconPlus />} 
                                size='md'
                                onClick={handleAddTransfer}
                                disabled={students.find(s => s.studentName === selectedStudent) === undefined}
                            >
                                ADD
                            </Button>
                        </Flex>
                    </Flex>
                </Card>
                <CourseTable
                    handleRemoveItem={handleRemoveTransfer}
                    records={tableItems}
                />
                <Button 
                    leftIcon={<IconDeviceFloppy />}
                    onClick={handleFormTransfer}
                    disabled={students.find(s => s.studentName === selectedStudent) === undefined}
                >
                    SUBMIT TRANSFER FORM
                </Button>
            </Flex>
        </Center>
    );
}
 
export default CourseTransferPage;