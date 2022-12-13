import { Anchor, Box, Button, Center, Divider, FileButton, Flex, Group, Select, Stack, Table, Text, TextInput, Title } from "@mantine/core";
import { useForm } from "@mantine/form";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { makeCourseRequest } from "../../api/Student/CourseService";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useCourses } from "../../hooks/useCourses";
import { useUser } from "../../provider/UserProvider";
import { Course, CourseRequest, PreviousCourseRequest } from "../../types";

const CourseRequestPage = () => {
    const [searchedBilkentCourseInfo, setBilkentOnSearchChange] = useState('');
    const [isBilkentCourseEmpty, setIsBilkentCourseEmpty] = useState(false);
    const [syllabusFile, setSyllabusFile] = useState<File | null>(null);
    const axiosSecure = useAxiosSecure()

    //const [syllabusLink, setSyllabusLink] = useState('');
    const { user } = useUser()

    // TODO: Use courses instead of allCoursesBilkent
    const {data: courses, isLoading: isCoursesLoading, isError: isCoursesError} = useCourses(axiosSecure)
    const allCoursesBilkent = ["CS473 - Algorithms I", "CS342 - Operating Systems"];

    const { mutate: mutateCourseRequest, isError: isCourseRequestError, isLoading: isCourseRequestLoading } = useMutation({
        mutationKey: ['courseRequest'],
        mutationFn: (course: CourseRequest) => makeCourseRequest(axiosSecure, course, user!.id)   
    })

    const form = useForm({
        initialValues: {
            hostCourseCode: '',
            courseName: '',
            webpage: '',
            syllabusLink:''
        },
        validate: {
            hostCourseCode: (value) => value.length > 0 ? null : "Course code cannot be empty.",
            courseName: (value) => value.length > 0 ? null : "Course name cannot be empty.",
            webpage: (value) => value.length > 0 ? null : "Webpage cannot be empty.",
            syllabusLink: (value) => (value.length == 0) && !syllabusFile ?  "Syllabus link/file cannot be empty." : null
        }
    })
    const handleRequestCourse = () => {
        const validate = form.validate();
        if (searchedBilkentCourseInfo === '') {
            setIsBilkentCourseEmpty(true);
        }
        else {
            setIsBilkentCourseEmpty(false);
        }
        if (!syllabusFile){
            //TODO: warn the user about missing syllabus
            console.log("no syllabus");
        }
        if (!validate.hasErrors && !isBilkentCourseEmpty && syllabusFile) {
            // send request to api

            const courseRequest: CourseRequest ={
                requestId: null,
                studentId: user.id,
                hostCode: {...form.getInputProps('hostCourseCode')},
                name: {...form.getInputProps('courseName')},
                webpage: {...form.getInputProps('webpage')},
                syllabusLink: {...form.getInputProps('syllabusLink')},
                bilkentCode: searchedBilkentCourseInfo
            }
            mutateCourseRequest(courseRequest)
            console.log("error yok");
        }
    }

    const previouslyRequestedCoursesList = [
        { courseCode: "CS473", courseName: "Algorithms I", bilkentCredits: "4", ectsCredits: "6", requestStatus: 0 },
        { courseCode: "CS319", courseName: "OOSE", bilkentCredits: "3", ectsCredits: "6", requestStatus: 1 },
        { courseCode: "CS315", courseName: "Programming Languages", bilkentCredits: "3", ectsCredits: "6", requestStatus: 2 },
    ]

    const previouslyRequestedRows = previouslyRequestedCoursesList.map((course) => (
        <tr key={course.courseCode} >
            <td>{course.courseCode}</td>
            <td>{course.courseName}</td>
            <td>{course.bilkentCredits}</td>
            <td>{course.ectsCredits}</td>
            <td style={{maxWidth:"200"}}>{""}
                <Group>
                    {course.requestStatus == 0 ? <IconCheck color={"green"} /> : course.requestStatus == 1 ? <IconSearch color={"blue"} /> : <IconX color={"red"} />}
                    <Text color={course.requestStatus == 0 ? "green" : course.requestStatus == 1 ? "blue" : "red"}> {course.requestStatus == 0 ? "Approved" : course.requestStatus == 1 ? "Pending Approval" : "Rejected"}</Text>
                </Group>
            </td>
        </tr>
    ));

    return (
        <Center sx={{ height: '87vh' }}>
            <Box
                sx={{ minWidth: 300 }}
                mx="auto"
            >
                <Stack
                    spacing='xl'
                >
                    <form>
                        <Stack spacing={16}>
                            <TextInput
                                label="Course Code"
                                placeholder="Course code at host university"
                                {...form.getInputProps('hostCourseCode')}
                            />
                            <TextInput
                                label="Course Name"
                                placeholder="Course name at host university"
                                {...form.getInputProps('courseName')}
                            >
                            </TextInput>
                            <TextInput
                                label="Course Webpage"
                                placeholder="Webpage of the course at host university"
                                {...form.getInputProps('webpage')}
                            >
                            </TextInput>
                            <Flex direction='row' align='center' justify="center" gap='lg'>
                                <FileButton onChange={setSyllabusFile} accept="application/pdf">
                                    {(props) => <Button {...props}>Upload Syllabus</Button>}
                                </FileButton>
                                <Title order={2}>OR</Title>
                                <TextInput
                                    label="Syllabus Link"
                                    placeholder="Syllabus link of the course at host university"
                                    {...form.getInputProps('syllabusLink')}
                                />
                                    
                            </Flex>
                            <Select
                                searchable
                                allowDeselect
                                error={isBilkentCourseEmpty ? "Please select a course from Bilkent" : ""}
                                label={"Corresponding course in Bilkent"}
                                nothingFound="No course in Bilkent Found"
                                onSearchChange={setBilkentOnSearchChange}
                                searchValue={searchedBilkentCourseInfo}
                                data={allCoursesBilkent} />
                            
                            {syllabusFile && (
                                <Text size="sm" align="center" mt="sm">
                                    Selected Syllabus: {syllabusFile.name}
                                </Text>
                            )}
                            <Button onClick={handleRequestCourse}>Request Course</Button>
                        </Stack>
                    </form>
                </Stack>
            </Box>
            <Divider orientation="vertical" />
            <Box
                sx={{ minWidth: 300 }}
                mx="auto"
            >
                <Stack
                    spacing='xl'
                >
                    <Table striped withBorder withColumnBorders>
                        <caption>Previously Requested Courses</caption>
                        <thead>
                            <tr>
                                <th>Course Code At Host University</th>
                                <th>Course Name At Host University</th>
                                <th>Bilkent Credits</th>
                                <th>ECTS Credits</th>
                                <th>Request Status</th>
                            </tr>
                        </thead>
                        <tbody>{previouslyRequestedRows}</tbody>
                    </Table>
                </Stack>
            </Box>
        </Center>);
}

export default CourseRequestPage;