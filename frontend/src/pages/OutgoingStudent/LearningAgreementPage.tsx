import { Anchor, Box, Button, Card, Center, Divider, FileButton, Flex, Group, Stack, TextInput, Title, Text, Dialog, Modal } from "@mantine/core";
import { useForm } from "@mantine/form";
import { IconCircleCheck } from "@tabler/icons";
import { useMutation } from "@tanstack/react-query";
import { SetStateAction, useState } from "react";
import { axiosSecure } from "../../api/axios";
import InfoModal from "../../components/modals/InfoModal";

const LearningAgreementPage = () => {
    const [file, setFile] = useState<File | null>(null)
    const [step1Modal, setStep1Modal] = useState(false);
    const [step2Modal, setStep2Modal] = useState(false);
    const [step3Modal, setStep3Modal] = useState(false);
    const laStatus: string = "Pending Approval from Coordinator"; // LA Status from api, make it enum?

    const handleGenerate = () => {
        //api'ye istek at
        setStep1Modal(true);
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
        setStep3Modal(true);
        setFile(payload);
    }
    return (
        <>
            <InfoModal infoOpened={step1Modal} info={"Your Learning Approval is sent to your Coordinator. After your coordinator approves the Learning Agreement, you can download the signed Learning Agreement and send it to host university to sign."} setInfoOpened={setStep1Modal}/>
            <InfoModal infoOpened={step2Modal} info={"Please send the downloaded Learning Agreement to host university to sign, then proceed to step 3 to upload the Learning Agreement signed by the host university."} setInfoOpened={setStep2Modal}/>
            <InfoModal infoOpened={step3Modal} info={"Please make sure that the uploaded file is signed by: You, your Coordinator and host university."} setInfoOpened={setStep3Modal}/>
            <Center sx={{ height: '87vh' }}>
                <Flex direction={"row"} gap={"lg"}>
                    <Box
                        sx={{ minWidth: 300, minHeight: 500 }}
                        mx="auto">
                        <Stack spacing={"xl"}>
                            <Title size='30px'>1) Generate & Sign</Title>
                            <Button onClick={handleGenerate}>Generate Learning Agreement & Send To Coordinator</Button>
                        </Stack>
                    </Box>
                    <Divider orientation="vertical" />
                    <Box>
                        <Center>
                            <Flex direction={"column"} gap={"xl"}>
                                <Title size='30px'>2) Send Coord. Signed Form to Host</Title>
                                <Card shadow='sm' p='xl' radius='md' withBorder>
                                    <Stack spacing={"xl"}>
                                        <Title align="center" order={2} weight="bold">Learning Agreement Status</Title>
                                        <Group position='center'>
                                            <Text>{laStatus}</Text>
                                            {/* <IconCircleCheck size={36} color="green" /> */}
                                        </Group>
                                    </Stack>
                                </Card>
                                <Button disabled={laStatus !== "Coordinator Approved"}>Download Coordinator Approved Learning Agreement</Button>
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
                            <Title size='30px'>3) Upload to OISEP</Title>
                            <Card miw={350} p='xl' shadow='xs' radius='md' withBorder>
                                <Title align="center" mb={30} inline order={2}>Upload Final Form of Learning Agreement</Title>
                                <Flex gap='xl'>
                                    <Flex align='center' direction='column' gap='md'>
                                        <FileButton onChange={handleFileUpload} accept="application/pdf">
                                            {(props) => <Button disabled={laStatus != "Approved by Coordinator"} size="lg" variant="outline" {...props}>Upload File</Button>}
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
            </Center></>



    );
}

export default LearningAgreementPage;