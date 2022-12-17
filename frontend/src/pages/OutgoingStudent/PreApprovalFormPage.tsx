import { Anchor, Button, Card, Divider, FileButton, Flex, Group, Stack, Text, Title } from "@mantine/core";
import { IconCircleCheck, IconDownload, IconTrash } from '@tabler/icons';
import { useMutation } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { deleteForm, downloadForm, submitFile } from '../../api/FileService';
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { usePreApprovalStatus } from "../../hooks/usePreApprovalStatus";
import { useUser } from "../../provider/UserProvider";
import { Form } from "../../types";
import { downloadBlobFile } from "../../utils/helpers";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const PreApprovalFormPage = () => {
    const { user } = useUser()
    const [file, setFile] = useState<File | null>(null)
    const axiosSecure = useAxiosSecure('multipart/form-data')

    // Fetch pre approval status from backend.
    // const { data: preApprovalFile, isLoading: isPreApprovalLoading, isError: isPreApprovalError } = usePreApprovalStatus(axiosSecure)
    // if (isPreApprovalLoading) {
    //     return <LoadingPage />
    // }


    // if (isPreApprovalError || !preApprovalFile) {
    //     return <ErrorPage />
    // }

    const manualUploadMutation = useMutation({
        mutationKey: ['fileSubmit'],
        mutationFn: (formData: FormData) => submitFile(axiosSecure, formData, user.id),
        onSuccess: () => toast.success("Uploaded the file successfully."),
        onError: () => toast.success("Please ensure that you deleted your previous file before uploading a new one.")
    })

    const fileDownloadMutation = useMutation({
        mutationKey: ['fileDownload'],
        mutationFn: () => downloadForm(axiosSecure, user.id, Form.PRE_APPROVAL),
        onSuccess: (data) => downloadBlobFile(data, `${Form.PRE_APPROVAL}_${user.bilkentId}`),
        onError: () => toast.error("You should upload a file before trying to download it.")
    })

    const fileDeleteMutation = useMutation({
        mutationKey: ['fileDelete'],
        mutationFn: () => deleteForm(axiosSecure, user.id, Form.PRE_APPROVAL),
        onSuccess: () => toast.success("Successfully deleted the file"),
        onError: () => toast.error("We couldn't delete the file. Please try again later.")
    })


    // https://stackoverflow.com/questions/53914361/upload-a-file-in-react-and-send-it-to-an-express-server
    const handleFileUpload = (payload: File | null) => {
        setFile(payload)
    }

    const handleFileSubmit = () => {
        if (file) {
            let formData = new FormData()
            formData.append('file', file)
            formData.append("fileType", "PRE_APPROVAL")
            manualUploadMutation.mutate(formData)
            console.log(manualUploadMutation.data)
        }
        else {
            toast.error("You should upload a file before submitting.")
        }
    }

    const handleFileDownload = () => {
        fileDownloadMutation.mutate()
    }

    const handleFileDelete = () => {
        fileDeleteMutation.mutate()
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
                    <Divider />
                    <Group spacing={24} position='center'>
                        <Text fw={600}>Approved</Text>
                        <IconCircleCheck size={36} color="green"/>
                        <Button 
                            loading={fileDownloadMutation.isLoading}
                            leftIcon={<IconDownload />} 
                            variant='outline' 
                            onClick={handleFileDownload}
                        >
                                Download your file
                        </Button>
                        <Button
                            loading={fileDeleteMutation.isLoading}
                            leftIcon={<IconTrash />}
                            variant='outline'
                            onClick={handleFileDelete}
                            color='red'
                        >
                            Remove Uploaded File
                        </Button>
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
                                loading={manualUploadMutation.isLoading}
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