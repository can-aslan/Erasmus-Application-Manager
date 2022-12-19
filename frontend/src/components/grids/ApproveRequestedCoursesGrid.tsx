import { Anchor, Button, Card, Center, Flex, Modal, SimpleGrid, Space, Stack, Text, TextInput } from "@mantine/core";
import { IconBook2, IconWorld } from "@tabler/icons";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { SetStateAction, useState } from "react";
import { toast } from "react-toastify";
import { changeRequestStatus } from "../../api/Instructor/CourseRequestService";
import { axiosSecure } from "../../api/axios";
import { useUser } from "../../provider/UserProvider";
import { CourseRequest, InstructorCourseRequestChange } from "../../types";
import CapybaraLottie from "../Loader/CapybaraLottie";

interface ApproveRequestedCoursesGridProps {
    waitingCourses: CourseRequest[];
}

const ApproveRequestedCoursesGrid = ({ waitingCourses }: ApproveRequestedCoursesGridProps) => {
    const [rejectionFeedback, setRejectionFeedback] = useState("");
    const [rejectionFeedbackOpened, setRejectionFeedbackOpened] = useState(false);
    const [selectedRequestId, setSelectedRequestId] = useState("");
    const { user } = useUser();
    const queryClient = useQueryClient();
    const { mutate: mutateChangeRequestStatus, data: courseRequestData, isLoading: isChaneStatusLoading } = useMutation({
        mutationKey: ['changeRequestStatus'],
        mutationFn: (requestBody: InstructorCourseRequestChange) => changeRequestStatus(axiosSecure, requestBody),
        onSuccess: () => {
            queryClient.invalidateQueries(['getWaitingRequests']);
            toast.success(`Course request status successfully changed.`);
        },
        onError: () => toast.error("Course request status change failed.")
    })


    const changeRequest = (newStatus: string, requestID: string) => {
        const requestBody: InstructorCourseRequestChange = {
            courseStatus: newStatus,
            instructorId: user.id,
            courseRequestId: requestID,
        }
        mutateChangeRequestStatus(requestBody);
    }
    return (
        <>
            <Modal opened={rejectionFeedbackOpened}
                size={"50%"}
                centered={false}
                onClose={() => setRejectionFeedbackOpened(false)}
                withCloseButton={false}
                closeOnClickOutside={false}
                closeOnEscape={false}
            >
                <Flex direction={"column"} gap={"xs"}>
                    <TextInput value={rejectionFeedback} onChange={(event) => setRejectionFeedback(event.currentTarget.value)} label={"Rejection Feedback"} placeholder={"Please a feedback for your rejection..."}></TextInput>
                    <Flex justify={"right"} gap={"xs"}>
                        <Button color={"red"} onClick={() => { setRejectionFeedbackOpened(false); setRejectionFeedback(""); }}>Cancel Rejection</Button>
                        <Button color={"green"} onClick={() => { setRejectionFeedbackOpened(false); changeRequest("REJECTED", selectedRequestId) }}>Confirm Rejection</Button>
                    </Flex>

                </Flex>

            </Modal>
            {waitingCourses.length === 0 && <Center sx={{ height: '100vh' }}>
                <Stack align='center'>
                    <CapybaraLottie />  
                    <Text size={22} color='blue'>
                        
                         You do not have any pending requests!</Text>
                </Stack>
            </Center>}
            
            <SimpleGrid cols={2}>
                {waitingCourses.map(course => (
                    <Card>
                        <Flex direction={"row"} gap={"sm"}>
                            <Flex direction={"column"} gap={"sm"}>
                                <TextInput disabled label={course.hostUniName} value={course.name + ` ${course.hostCode}`}></TextInput>
                                <Anchor color='white' target="_blank" href={course.syllabusLink} span><Button leftIcon={<IconBook2 />} color={'blue'}>{"Course Syllabus"}</Button></Anchor>
                                <Button color={'red'} onClick={() => { setRejectionFeedbackOpened(true); setSelectedRequestId(course.requestId!) }}>Reject</Button>
                            </Flex>
                            <Flex direction={"column"} gap={"sm"} >
                                <TextInput disabled label={"Bilkent"} value={course.bilkentCode}></TextInput>
                                <Anchor target="_blank" href={course.webpage} span><Button leftIcon={<IconWorld />} color={'blue'}>{"Course Webpage"}</Button></Anchor>
                                <Button color={'green'} onClick={() => { changeRequest("APPROVED", course.requestId!) }}>Approve</Button>
                            </Flex>

                        </Flex>
                    </Card>
                ))}
            </SimpleGrid>
        </>
    );
}

export default ApproveRequestedCoursesGrid;