import { Autocomplete, Card, Center, Flex, SimpleGrid, Text, TextInput } from "@mantine/core";
import { IconArrowLeftRight } from '@tabler/icons';
import { useState } from "react";
import { CoordinatorAssociatedStudents, Course } from "../../types";

const CourseTransferFormHeader = () => {
    return (
        <>
            <Text size={22} fw={600} ta='center'>Course in host university</Text>
            <Text size={22} fw={600} ta='center'>Course in Bilkent</Text>
        </>
    )
}

const CourseTransferPage = () => {
    const [selectedStudent, setSelectedStudent] = useState('')

    // TODO: Replace this variable with database request
    const students: Array<CoordinatorAssociatedStudents> = [
        {
            file: "file",
            formUuid: "form1",
            studentName: "Selim Can Güler",
            studentUuid: "student1",
        },
        {
            file: "file",
            formUuid: "form2",
            studentName: "Elif Kervan",
            studentUuid: "student2",
        },
        {
            file: "file",
            formUuid: "form2",
            studentName: "Kubilaylaylay",
            studentUuid: "student3",
        },
    ]

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


    // TODO: Fetch Bilkent courses
    const courses = bilkentCourses.map(c => {
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

    return (
        <Center sx={{height: "40vh"}}>
            <Flex direction="column" gap={36}>
                <Autocomplete 
                    data={data}
                    placeholder="Select a student"
                    onChange={(value) => setSelectedStudent(value)}
                />
                <Card
                    p={48}
                    shadow="lg"
                    radius="lg"
                >
                    <SimpleGrid
                        cols={2}
                        spacing={36}
                    >
                        <CourseTransferFormHeader />
                        <TextInput
                            placeholder="Course taken in the host university"
                        />
                        <Autocomplete 
                            placeholder="Correspondant course in Bilkent"
                            data={courses}    
                        />
                    </SimpleGrid>
                </Card>
            </Flex>
        </Center>
    );
}
 
export default CourseTransferPage;