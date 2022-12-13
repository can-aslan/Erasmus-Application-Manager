import { Button, Card, Center, Flex, Group, Modal, Select, Space, Table, Text, TextInput } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useState } from "react";
import { LearningAgreement } from "../../types";
import RejectionFeedbackModal from "../modals/RejectionFeedbackModal"
interface ApproveLearningAgreementsTableProps {
    learningAgreements: Array<LearningAgreement>
}
const ApproveLearningAgreementsTable = ({ learningAgreements }: ApproveLearningAgreementsTableProps) => {
    const [rejectionFeedbackOpened, setRejectionFeedbackOpened] = useState(false);
    const [rejectionFeedback, setRejectionFeedback] = useState("");

    const [tempPendingLearningAgreements, setTempPendingLearningAgreements] = useState(learningAgreements);
    const [selectedAgreementStatusFilter, setSelectedAgreementStatusFilter] = useState<string | null>(null);
    const [searchLAInput, setSearchLAInput] = useState('');

    const approvePreApproval = (formID: string) => {
        //Approve the selected student's wishlist

    }
    const rejectPreApproval = (formID: string) => {
        // Reject the selected student's wishlist
        setRejectionFeedbackOpened(true);
    }
    const downloadLearningAgreement = (formID: string) => {
        //TODO: download selected learning agreement
    }

    const agreementListRows = getPendingAgreementRows();

    const changeFilter = (filter: string | null, query: string) => {
        setSelectedAgreementStatusFilter(filter);
        searchPreApproval(query, filter);
    }

    const changeSearch = (value: string, filter: string | null) => {
        setSearchLAInput(value);
        searchPreApproval(value, filter);
    }
    const searchPreApproval = (query: string, filter: string | null) => {
        if (query !== '') {
            if (filter === null) {
                setTempPendingLearningAgreements(learningAgreements.filter((student) => student.studentName.toLowerCase().includes(query.toLowerCase()) || student.studentId.toString().includes(query) || student.rejectionFeedback.toLowerCase().includes(query.toLowerCase())));
            }
            else {
                setTempPendingLearningAgreements(learningAgreements.filter((student) => (student.studentName.toLowerCase().includes(query.toLowerCase()) || student.studentId.toString().includes(query) || student.rejectionFeedback.toLowerCase().includes(query.toLowerCase())) && student.status === filter));
            }
        }
        else {
            if (filter === null) {
                setTempPendingLearningAgreements(learningAgreements);
            } else {
                setTempPendingLearningAgreements(learningAgreements.filter((student) => student.status === filter));
            }

        }
    }

    return (
        <>
            <RejectionFeedbackModal rejectionFeedbackOpened={rejectionFeedbackOpened} rejectionFeedback={rejectionFeedback} setRejectionFeedbackOpened={setRejectionFeedbackOpened} setRejectionFeedback={setRejectionFeedback}></RejectionFeedbackModal>

            <Flex direction="column" gap={"sm"}>
                <Flex direction="row" gap={"xl"}>
                    <TextInput placeholder="Seach by name or ID" value={searchLAInput} onChange={(event) => changeSearch(event.currentTarget.value, selectedAgreementStatusFilter)}></TextInput><Select placeholder="Filter by status" value={selectedAgreementStatusFilter} onChange={(value) => { changeFilter(value, searchLAInput) }} clearable allowDeselect data={["Approved", "Pending", "Rejected"]}></Select>
                </Flex>
                <Table striped withBorder withColumnBorders>
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Student ID</th>
                            <th>Download Learning Agreement</th>
                            <th>Learning Agreement Status</th>
                            <th>Approve / Reject Learning Agreement</th>
                            <th>Rejection Feedback</th>
                        </tr>
                    </thead>
                    <tbody>{agreementListRows}</tbody>
                </Table>
            </Flex></>);
    function getPendingAgreementRows() {
        return tempPendingLearningAgreements.map((element) => (
            <tr key={element.studentName}>
                <td>{element.studentName}</td>
                <td>{element.studentId}</td>
                <td>{""}
                    <Center>
                        <Button onClick={() => { downloadLearningAgreement(element.formUuid) }}>Download</Button>
                    </Center></td>
                <td>
                    <Center>
                        <Card maw={200} sx={(theme) => ({
                            background: element.status == "Approved" ? "#2f9e44" : element.status == "Pending" ? "#1971c2" : "#e03131"
                        })}
                            radius={"lg"}>
                            <Flex gap={"xs"} justify={"center"}>
                                {element.status == "Approved" ? <IconCheck /> : element.status == "Pending" ? <IconSearch /> : <IconX />}
                                <Text size={"sm"} weight={500} color={"white"}>{element.status}</Text>
                            </Flex>
                        </Card></Center>
                </td>
                <td>{""}
                    <Center>
                    <Flex gap={"sm"}>
                         {(element.status == "Pending" || element.status == "Rejected") && <Button color={'green'} onClick={() => { approvePreApproval(element.formUuid) }}>Approve</Button>}
                          {(element.status == "Pending" || element.status == "Approved") && <Button color={'red'} onClick={() => { rejectPreApproval(element.formUuid) }}>Reject</Button>}
                    </Flex>    
                    </Center>
                </td>
                <td>{element.rejectionFeedback}</td>
            </tr>
        ));
    }
}

export default ApproveLearningAgreementsTable;