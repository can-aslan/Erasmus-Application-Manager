import { Card, Group, Stack, Text, Title } from "@mantine/core";
import { IconBan, IconCircleCheck, IconClockPause } from "@tabler/icons";
import { ApprovalStatus } from "../../types";

interface StatusFeedbackProps {
    title: string,
    status: ApprovalStatus,
    feedback?: string | null
}

const StatusFeedback = ({ title, status, feedback}: StatusFeedbackProps) => {
    return (
        <Card maw={300} shadow='sm' p='xl' radius='md' withBorder>
            <Stack>
                <Title align="center" order={2} weight="bold">{title}</Title>
                <Group position='center'>
                    <Text>{status}</Text>
                    {status === "APPROVED" 
                        ? <IconCircleCheck size={32} color="green"/> 
                            : status === "PENDING" 
                            ? <IconClockPause size={32} color='blue' /> : <IconBan size={32} color='red'/>}
                    {status === "REJECTED" && feedback &&<Text>Rejection Feedback: {feedback}</Text>}
                </Group>
            </Stack>
        </Card>
    );
}
 
export default StatusFeedback;