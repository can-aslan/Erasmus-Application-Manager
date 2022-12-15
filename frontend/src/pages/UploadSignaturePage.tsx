import { Button, FileButton, Flex } from "@mantine/core";
import { useState } from "react";

const UploadSignaturePage = () => {
    const [signatureFile, setSignatureFile] = useState<File | null>()

    return (
        <Flex
            gap='xl'
            direction='column'
        >
            <FileButton
                onChange={setSignatureFile}
                >
                {(props) => <Button {...props}>Upload your signature</Button>}
            </FileButton>
            <Button>
                Save Signature
            </Button>
        </Flex>
    );
}
 
export default UploadSignaturePage;