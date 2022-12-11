import { Anchor, Button, Card, Divider, FileButton, Flex, Group, Stack, Text, Title } from "@mantine/core";
import { IconCircleCheck } from '@tabler/icons';
import { useState } from "react";
import { usePreApprovalStatus } from "../../hooks/usePreApprovalStatus";
import { useUser } from "../../provider/UserProvider";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const PreApprovalFormPage = () => {
    const [file, setFile] = useState<File | null>(null)
    const { user } = useUser()
    // Fetch pre approval status from backend.
    const { data: preApprovalFile, isLoading: isPreApprovalLoading, isError: isPreApprovalError } = usePreApprovalStatus(user.id)
    if (isPreApprovalLoading) {
        return <LoadingPage />
    }

    if (isPreApprovalError || !preApprovalFile) {
        return <ErrorPage />
    }

    // https://stackoverflow.com/questions/53914361/upload-a-file-in-react-and-send-it-to-an-express-server
    const handleFileUpload = (payload: File | null) => {
        setFile(payload)
    }

    const handleFileSubmit = () => {
        // TODO: Submit the file to backend
    }

    const handleGenerateAndDownload = () => {
        // TODO: Generate pre approval form and download
    }

    const handleGenerateAndSubmit = () => {
        // TODO:
    }

    return (
        <Flex align='center' justify='center' direction='column' gap={100}>
            <Card maw={300} shadow='sm' p='xl' radius='md' withBorder>
                <Stack>
                    <Title order={2} weight="bold">Pre-approval Status</Title>
                    <Group position='center'>
                        <Text>Approved</Text>
                        <IconCircleCheck size={36} color="green"/>
                    </Group>
                </Stack>
            </Card>
            <Flex w='100' gap={150} align='center'>
                <Card miw={350} p='xl' shadow='xs' radius='md' withBorder>
                    <Title align="center" mb={30} inline order={2}>Manual Upload</Title>
                    <Flex gap='xl'>
                        <Flex align='center' direction='column' gap='md'>
                            <FileButton onChange={handleFileUpload} accept="application/pdf">
                                {(props) => <Button size="lg" variant="outline" {...props}>Upload File</Button>}
                            </FileButton>
                            {file ? <Anchor href={window.URL.createObjectURL(file)}>{file.name}</Anchor> : <Text color='dimmed'>The uploaded file will appear here</Text>}
                        </Flex>
                        <Divider orientation="vertical"/>
                        <Group position="center">
                            <Button 
                                disabled={file === null} 
                                size="lg"
                                onClick={handleFileSubmit}
                            >
                                Submit
                            </Button>
                        </Group>
                    </Flex>
                </Card>
                <Title order={1}>OR</Title>
                <Group position="center">
                    <Stack spacing={50}>
                        <Button onClick={handleGenerateAndDownload} size="lg">Generate & Download Pre-approval Form</Button>
                        <Button onClick={handleGenerateAndSubmit} size="lg">Generate & Submit Pre-approval Form</Button>
                    </Stack>
                </Group>
            </Flex>
        </Flex>

    );
}
 
export default PreApprovalFormPage;