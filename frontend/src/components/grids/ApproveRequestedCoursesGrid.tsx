import { SimpleGrid, Card, Flex, TextInput, Button, Modal, Space } from "@mantine/core";
import { IconBook2, IconWorld } from "@tabler/icons";
import { useMutation, useQuery } from "@tanstack/react-query";
import { SetStateAction, useState } from "react";
import { toast } from "react-toastify";
import { axiosSecure } from "../../api/axios";
import { changeRequestStatus } from "../../api/Instructor/CourseRequestService";
import { getStudent } from "../../api/StudentService";
import { useUser } from "../../provider/UserProvider";
import { CourseRequest, InstructorCourseRequestChange } from "../../types";
import RejectionFeedbackModal from "../modals/RejectionFeedbackModal";

interface ApproveRequestedCoursesGridProps {
    waitingCourses: CourseRequest[];
}

const ApproveRequestedCoursesGrid = ({ waitingCourses }: ApproveRequestedCoursesGridProps) => {
    const [rejectionFeedback, setRejectionFeedback] = useState("");
    const [rejectionFeedbackOpened, setRejectionFeedbackOpened] = useState(false);
    const [selectedRequestId, setSelectedRequestId] = useState("");
    const { user } = useUser();

    const { mutate: mutateChangeRequestStatus, data: courseRequestData, isLoading: isChaneStatusLoading } = useMutation({
        mutationKey: ['changeRequestStatus'],
        mutationFn: (requestBody: InstructorCourseRequestChange) => changeRequestStatus(axiosSecure, requestBody),
        onSuccess: () => toast.success(`Course request status successfully changed.`),
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
            <SimpleGrid cols={2}>
                {waitingCourses.map(course => (
                    <Card>
                        <Flex direction={"row"} gap={"sm"}>
                            <Flex direction={"column"} gap={"sm"}>
                                <TextInput disabled label={"asd"} value={course.name + ` ${course.hostCode}`}></TextInput>
                                <Button leftIcon={<IconBook2 />} color={'blue'} onClick={() => { }}>Course Syllabus</Button>
                                <Button color={'red'} onClick={() => { setRejectionFeedbackOpened(true); setSelectedRequestId(course.requestId!)}}>Reject</Button>
                            </Flex>
                            <Flex direction={"column"} gap={"sm"} >
                                <TextInput disabled label={"Bilkent"} value={course.bilkentCode}></TextInput>
                                <Button leftIcon={<IconWorld />} color={'blue'} onClick={() => { }}>Course Webpage</Button>
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