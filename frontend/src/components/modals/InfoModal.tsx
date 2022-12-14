import { Button, Flex, Modal, Text } from "@mantine/core";

interface InfoModalProps{
    infoOpened: boolean;
    info: string;
    setInfoOpened: React.Dispatch<React.SetStateAction<boolean>>;
}
const InfoModal = ({infoOpened, info, setInfoOpened}: InfoModalProps) => {
    return ( 
        <Modal
                opened={infoOpened}
                withCloseButton
                onClose={() => setInfoOpened(false)}
                size="lg"
                radius="md"
            >
                <Flex direction={"column"} gap={"sm"}>
                    <Text> {info}
                    </Text>
                    <Button color={'green'} onClick={() => { setInfoOpened(false); }}>OK</Button>
                </Flex>
            </Modal>
     );
}
 
export default InfoModal;