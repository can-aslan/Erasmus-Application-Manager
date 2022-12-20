import { Anchor, Box, Button, Center, Divider, FileButton, Flex, Group, Select, Stack, Switch, Table, Text, TextInput, Title } from "@mantine/core";
import { useForm } from "@mantine/form";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { toast } from "react-toastify";
import { getPreviouslyRequestedCourses, makeCourseRequest } from "../../api/Student/CourseService";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useCourses } from "../../hooks/useCourses";
import { useUser } from "../../provider/UserProvider";
import { Course, CourseRequest, PreviousCourseRequest } from "../../types";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const ELECTIVE_TYPES = ["General Elective", "Technical Elective", "Arts Core Elective", "Social Sciences Elective"]

const CourseRequestPage = () => {
    const [searchedBilkentCourseInfo, setBilkentOnSearchChange] = useState('');
    const [isBilkentCourseEmpty, setIsBilkentCourseEmpty] = useState(false);
    const [isElective, setIsElective] = useState(false);
    const axiosSecure = useAxiosSecure();
    const queryClient = useQueryClient();
    const { user } = useUser();


    const { data: courses, isLoading: isCoursesLoading, isError: isCoursesError } = useCourses(axiosSecure)
    const { data: dataPrevious, isError: isPreviousError, isLoading: isPreviousLoading } = useQuery({
        queryFn: () => getPreviouslyRequestedCourses(axiosSecure),
        queryKey: ['previousCourseRequests']
    })
    const { mutate: mutateCourseRequest, isError: isCourseRequestError, isLoading: isCourseRequestLoading } = useMutation({
        mutationKey: ['courseRequest'],
        mutationFn: (course: CourseRequest) => makeCourseRequest(axiosSecure, course),
        onSuccess: () => {
            toast.success("Course requested successfully!");
            queryClient.invalidateQueries(['previousCourseRequests'])
        },
        onError: () => {
            toast.error("Course request failed.");
        }
    })

    const form = useForm({
        initialValues: {
            hostCourseCode: '',
            courseName: '',
            webpage: '',
            syllabusLink: '',
            ectsCredits: ''
        },
        validate: {
            hostCourseCode: (value) => value.length > 0 ? null : "Course code cannot be empty.",
            courseName: (value) => value.length > 0 ? null : "Course name cannot be empty.",
            webpage: (value) => value.length > 0 ? null : "Webpage cannot be empty.",
            syllabusLink: (value) => value.length > 0 ? null : "Syllabus link/file cannot be empty.",
            ectsCredits: (value) => value.length > 0 ? null : "ECTS Credits cannot be empty.",
        }
    })
    if (isPreviousLoading || isCoursesLoading) {
        return (
            <LoadingPage />
        )
    }

    if (isPreviousError || isCoursesError || !dataPrevious) {
        return (
            <ErrorPage />
        )
    }

    const allCoursesBilkent = courses.data.map(c => c.courseCode);

    const handleRequestCourse = () => {
        const validate = form.validate();
        if (searchedBilkentCourseInfo === '') {
            setIsBilkentCourseEmpty(true);
        }
        else {
            setIsBilkentCourseEmpty(false);
        }
        if (!validate.hasErrors && !isBilkentCourseEmpty) {
            // send request to api

            const courseRequest: CourseRequest = {
                studentId: user.bilkentId,
                hostCode: form.values.hostCourseCode,
                name: form.values.courseName,
                webpage: form.values.webpage,
                syllabusLink: form.values.syllabusLink,
                hostEcts: form.values.ectsCredits,
                bilkentCode: searchedBilkentCourseInfo,
                destination: isElective ? 'COORDINATOR' : 'INSTRUCTOR',
            }
            mutateCourseRequest(courseRequest)
            console.log("error yok");
        }
    }

    const previouslyRequestedCoursesList: Array<CourseRequest> = dataPrevious.data;
    const previouslyRequestedRows = previouslyRequestedCoursesList.map((course) => (
        <tr key={course.requestId} >
            <td>{course.hostCode}</td>
            <td>{course.name}</td>
            <td>{course.hostEcts}</td>
            <td>{course.bilkentCode}</td>
            <td style={{ maxWidth: "200" }}>{""}
                <Group>
                    {course.status == 'APPROVED' ? <IconCheck color={"#2f9e44"} /> : course.status == 'PENDING' ? <IconSearch color={"#1971c2"} /> : <IconX color={"#e03131"} />}
                    <Text color={course.status == 'APPROVED' ? "#2f9e44" : course.status == 'PENDING' ? "#1971c2" : "#e03131"}>
                        {course.status == 'APPROVED' ? "Approved" : course.status == 'PENDING' ? "Pending Approval" : "Rejected"}
                    </Text>
                </Group>
            </td>
            <td style={{wordWrap:"break-word"}}>{course.feedback}</td>
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
                            <TextInput
                                label="ECTS Credits"
                                placeholder="ECTS Credits of the course at host university"
                                {...form.getInputProps('ectsCredits')}
                            ></TextInput>
                            <TextInput
                                label="Syllabus Link"
                                placeholder="Syllabus link of the course at host university"
                                {...form.getInputProps('syllabusLink')} />

                            <Switch label={"Is Elective"} checked={isElective} onChange={(event) => setIsElective(event.currentTarget.checked)} />
                            <Select
                                searchable
                                allowDeselect
                                error={isBilkentCourseEmpty ? "Please select a course from Bilkent" : ""}
                                label={isElective ? "Elective Type" : "Corresponding course in Bilkent"}
                                nothingFound="No elective in Bilkent Found"
                                onSearchChange={setBilkentOnSearchChange}
                                searchValue={searchedBilkentCourseInfo}
                                data={isElective ? ELECTIVE_TYPES : allCoursesBilkent} />
                            <Button
                                onClick={handleRequestCourse}
                                loading={isCourseRequestLoading}
                            >
                                Request Course
                            </Button>
                        </Stack>
                    </form>
                </Stack>
            </Box>
            <Divider orientation="vertical" />
            <Box
                sx={{ minWidth: 200 }}
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
                                <th>ECTS Credits</th>
                                <th>Bilkent Equivalent</th>
                                <th>Request Status</th>
                                <th>Rejection Feedback</th>
                            </tr>
                        </thead>
                        <tbody>{previouslyRequestedRows}</tbody>
                    </Table>
                </Stack>
            </Box>
        </Center>);
}

export default CourseRequestPage;