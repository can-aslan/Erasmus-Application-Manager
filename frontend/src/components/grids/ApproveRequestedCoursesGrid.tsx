import { SimpleGrid, Card, Flex, TextInput, Button } from "@mantine/core";
import { CourseRequest } from "../../types";

interface ApproveRequestedCoursesGridProps{
    waitingCourses: CourseRequest [];
}

const ApproveRequestedCoursesGrid = ({waitingCourses}:ApproveRequestedCoursesGridProps) => {
    
    return (
        <SimpleGrid cols={2}>
            {waitingCourses.map(course => (
                <Card>
                    <Flex direction={"column"} gap={"sm"}>
                    <Flex direction={"row"} gap={"xl"}>
                        <TextInput disabled label={"Host name from somewhere?"} value={course.courseName + ` ${course.courseCode}`}></TextInput>
                        <TextInput disabled label={"Bilkent"} value={course.correspondingCourseInBilkent}></TextInput>
                    </Flex>
                    <Flex direction={"row"} gap={"xs"} justify={"right"}>
                        <Button color={'red'} onClick={() => { }}>Reject</Button>
                        <Button color={'green'} onClick={() => { }}>Approve</Button>
                    </Flex>
                    </Flex>
                </Card>
            ))}
        </SimpleGrid>
    );
}

export default ApproveRequestedCoursesGrid;