import { Modal, Flex, TextInput, Button } from "@mantine/core";
interface RejectionFeedbackModalProps{
    rejectionFeedbackOpened: boolean;
    rejectionFeedback: string;
    setRejectionFeedbackOpened: React.Dispatch<React.SetStateAction<boolean>>;
    setRejectionFeedback: React.Dispatch<React.SetStateAction<string>>
}
const RejectionFeedbackModal = ({rejectionFeedback, rejectionFeedbackOpened, setRejectionFeedbackOpened, setRejectionFeedback}: RejectionFeedbackModalProps) => {
    return (<Modal opened={rejectionFeedbackOpened}
        size={"50%"}
        centered={false}
        onClose={() => setRejectionFeedbackOpened(false)}
        withCloseButton={false}
        closeOnClickOutside={false}
        closeOnEscape={false}
    >
        <Flex direction={"column"} gap={"xs"}>
            <TextInput value={rejectionFeedback} onChange={(event) => setRejectionFeedback(event.currentTarget.value)} label={"Rejection Feedback"} placeholder={"Please a feedback for your rejection..."}></TextInput>
            <Flex justify={"right"} gap={"xs"}>
                <Button color={"red"} onClick={() => { setRejectionFeedbackOpened(false); setRejectionFeedback(""); }}>Cancel Rejection</Button>
                <Button color={"green"} onClick={() => { setRejectionFeedbackOpened(false); setRejectionFeedback(rejectionFeedback); }}>Confirm Rejection</Button>
            </Flex>

        </Flex>

    </Modal>);
}

export default RejectionFeedbackModal;