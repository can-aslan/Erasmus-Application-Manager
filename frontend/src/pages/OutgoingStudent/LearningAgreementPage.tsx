import { Anchor, Box, Button, Card, Center, Divider, FileButton, Flex, Group, Stack, TextInput, Title, Text } from "@mantine/core";
import { useForm } from "@mantine/form";
import { IconCircleCheck } from "@tabler/icons";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { axiosSecure } from "../../api/axios";

const LearningAgreementPage = () => {
    const [file, setFile] = useState<File | null>(null)

    const laStatus: string = "Pending Approval from Coordinator"; // LA Status from api, make it enum?

    const handleGenerate = () => {
        //api'ye istek at
    }
    const handleFileSubmit = () => {
        if (file) {
            let formData = new FormData()
            formData.append('file', file)
            //manualUploadMutation.mutate(formData)
        }
        else {
            // TODO: Toast error notification
        }


    }
    const manualUploadMutation = useMutation({
        mutationKey: ['fileSubmit'],
        //TODO: submitLearningAgreementFile
        //mutationFn: (formData: FormData) => submitLearningAgreementFile(axiosSecure, formData, user.id)
    })
    // https://stackoverflow.com/questions/53914361/upload-a-file-in-react-and-send-it-to-an-express-server
    const handleFileUpload = (payload: File | null) => {
        setFile(payload)
    }
    return (
        <Center>
            <Flex direction={"row"} gap={"lg"}>
                <Box
                    sx={{ minWidth: 300, minHeight: 500 }}
                    mx="auto">
                    <Stack spacing={"xl"}>
                        <Title size='40px' >Step 1) Generate and Sign</Title>
                        <Button onClick={handleGenerate}>Generate Learning Agreement & Send To Coordinator</Button>
                    </Stack>
                </Box>
                <Divider orientation="vertical" />
                <Box>
                    <Center>
                        <Flex direction={"column"} gap={"xl"}>
                            <Title size='40px' >Step 2)</Title>
                            <Card maw={500} shadow='sm' p='xl' radius='md' withBorder>
                                <Stack spacing={"xl"}>
                                    <Title order={2} weight="bold">Learning Agreement Status</Title>
                                    <Group position='center'>
                                        <Text>{laStatus}</Text>
                                        {/* <IconCircleCheck size={36} color="green" /> */}
                                    </Group>
                                </Stack>
                            </Card>
                            <Button disabled={laStatus !== "Coordinator Approved"}>Dowload Coordinator Approved Learning Agreement</Button>
                        </Flex>

                    </Center>
                </Box>
                <Divider orientation="vertical" />
                <Box
                    sx={{ minWidth: 300 }}
                    mx="auto">
                    <Stack
                        spacing={"xl"}
                    >
                        <Title size='40px' >Step 3) Upload to OISEP</Title>
                        <Card miw={350} p='xl' shadow='xs' radius='md' withBorder>
                            <Title align="center" mb={30} inline order={2}>Upload Final Form of Learning Agreement</Title>
                            <Flex gap='xl'>
                                <Flex align='center' direction='column' gap='md'>
                                    <FileButton onChange={handleFileUpload} accept="application/pdf">
                                        {(props) => <Button size="lg" variant="outline" {...props}>Upload File</Button>}
                                    </FileButton>
                                    {file ? <Anchor href={window.URL.createObjectURL(file)}>{file.name}</Anchor> : <Text color='dimmed'>The uploaded file will appear here</Text>}
                                </Flex>
                                <Divider orientation="vertical" />
                                <Group position="center">
                                    <Button
                                        disabled={file === null || laStatus != "Approved by Coordinator"}
                                        size="lg"
                                        onClick={handleFileSubmit}
                                    >
                                        Submit
                                    </Button>
                                </Group>
                            </Flex>
                        </Card>
                    </Stack>
                </Box>
            </Flex>
        </Center>



    );
}

export default LearningAgreementPage;