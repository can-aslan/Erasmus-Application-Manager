import { SimpleGrid, Card, Flex, TextInput, Button, Modal, Space } from "@mantine/core";
import { IconBook2, IconWorld } from "@tabler/icons";
import { SetStateAction, useState } from "react";
import { CourseRequest } from "../../types";
import RejectionFeedbackModal from "../modals/RejectionFeedbackModal";

interface ApproveRequestedCoursesGridProps {
    waitingCourses: CourseRequest[];
    hostName: string;
}

const ApproveRequestedCoursesGrid = ({ waitingCourses, hostName }: ApproveRequestedCoursesGridProps) => {
    const [rejectionFeedback, setRejectionFeedback] = useState("");
    const [rejectionFeedbackOpened, setRejectionFeedbackOpened] = useState(false);

    return (
        <>
            <RejectionFeedbackModal rejectionFeedbackOpened={rejectionFeedbackOpened} rejectionFeedback={rejectionFeedback} setRejectionFeedbackOpened={setRejectionFeedbackOpened} setRejectionFeedback={setRejectionFeedback}></RejectionFeedbackModal>
            <SimpleGrid cols={2}>
                {waitingCourses.map(course => (
                    <Card>
                        <Flex direction={"row"} gap={"sm"}>
                            <Flex direction={"column"} gap={"sm"}>
                                <TextInput disabled label={hostName} value={course.courseName + ` ${course.courseCode}`}></TextInput>
                                <Button leftIcon={<IconBook2/>} color={'blue'} onClick={() => { }}>Course Syllabus</Button>
                                <Button color={'red'} onClick={() => { setRejectionFeedbackOpened(true) }}>Reject</Button>
                            </Flex>
                            <Flex direction={"column"} gap={"sm"} >
                                <TextInput disabled label={"Bilkent"} value={course.correspondingCourseInBilkent}></TextInput>
                                <Button leftIcon={<IconWorld/>} color={'blue'} onClick={() => { }}>Course Webpage</Button>
                                <Button color={'green'} onClick={() => { }}>Approve</Button>
                            </Flex>

                        </Flex>
                    </Card>
                ))}
            </SimpleGrid>
        </>
    );
}

export default ApproveRequestedCoursesGrid;