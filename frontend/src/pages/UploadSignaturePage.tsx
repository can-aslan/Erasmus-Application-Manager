import { Button, Card, FileButton, Flex, Text } from "@mantine/core";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { toast } from "react-toastify";
import { downloadSignature, submitSignature } from "../api/FileService";
import useAxiosSecure from "../hooks/useAxiosSecure";
import { useUser } from "../provider/UserProvider";
import { downloadBlobFile } from '../utils/helpers';

const UploadSignaturePage = () => {
    const { user } = useUser()
    const [signatureFile, setSignatureFile] = useState<File | null>(null)
    const axiosSecure = useAxiosSecure()

    const { mutate: submitSignatureMutate, isLoading: isSavingSignature, isError: isSavingError } = useMutation({
        mutationKey: ['uploadSignature'],
        mutationFn: (formData: FormData) => submitSignature(axiosSecure, formData, user.id),
        onSuccess: () => toast.success("Successfully saved the signature!")
    })

    const { mutate: downloadSignatureMutate, isLoading: isDownloadingSignature, isError: isDownloadingError } = useMutation({
        mutationKey: ['downloadSignature'],
        mutationFn: async () => await downloadSignature(axiosSecure, user.id),
        onSuccess: (data) => { 
            console.log(data)
            downloadBlobFile(data, "filename")
        },
        onError: () => toast.error("Error while trying to fetch the file from the database. Make sure you have uploaded your signature before trying to download.")
    })

    const handleSignatureSubmit = () => {
        const formData = new FormData()
        console.log(formData)
        if (signatureFile) {
            formData.append("signature", signatureFile)
            console.log(formData)
            submitSignatureMutate(formData)
        }
        else {
            toast.error("You should upload a file before saving.")
        }
    }

    return (
        <Card 
            shadow='xl' 
            radius='xl' 
            p={36}
            maw={450}
        >
            <Flex
                gap={36}
                direction='column'
            >
                <Flex gap='xs' direction='column'>
                    <Text color='dimmed'>*Only accepts .png files</Text>
                    <FileButton
                        onChange={setSignatureFile}
                        accept={"image/png"}
                    >
                        {(props) => <Button {...props}>Upload Signature</Button>}
                    </FileButton>
                </Flex>
                <Button
                    onClick={handleSignatureSubmit}
                    loading={isSavingSignature}
                >
                    Save Signature
                </Button>
                <Button
                    onClick={() => downloadSignatureMutate()}
                    loading={isDownloadingSignature}
                >
                    Download Signature
                </Button>
            </Flex>
        </Card>
    );
}
 
export default UploadSignaturePage;