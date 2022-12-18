import { Button, Center, Flex, Group, Modal, Select, Space, Table, Text, TextInput } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import React, { useEffect, useState } from "react";
import { approveSubmittedPreApprovalCoord, rejectSubmittedPreApprovalCord } from "../../api/Coordinator/PreapprovalService";
import { downloadForm } from "../../api/FileService";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useUser } from "../../provider/UserProvider";
import { Form, PreApprovalForm, PreApprovalFormItemType } from "../../types";
import { downloadBlobFile } from "../../utils/helpers";
import RejectionFeedbackModal from "../modals/RejectionFeedbackModal";

interface ApprovePreApprovalsTableProps {
    preApprovals: Array<PreApprovalForm>
}
const ApprovePreApprovalsTable = ({preApprovals}: ApprovePreApprovalsTableProps) => {   
    const axiosSecure = useAxiosSecure()
    const { user } = useUser()
    const queryClient = useQueryClient()

    const [preApprovalDetailsOpened, setPreApprovalDetailsOpened] = useState(false);
    const [rejectionFeedbackOpened, setRejectionFeedbackOpened] = useState(false);

    const [selectedStudentName, setSelectedStudentName] = useState("");
    const [selectedStudentID, setSelectedStudentID] = useState("");
    const [showReject, setShowReject] = useState(false);
    const [showApprove, setShowApprove] = useState(false);
    const [rejectionFeedback, setRejectionFeedback] = useState("");
    
    const [tempPendingApprovalList, setTempPendingApprovalList] = useState(preApprovals);
    const [selectedApprovalStatusFilter, setSelectedApprovalStatusFilter] = useState<string | null>(null);
    const [searchPreAppInput, setSearchPreAppInput] = useState('');
    const [selectedPreApproval, setSelectedPreApproval] = useState<Array<PreApprovalFormItemType>>();

    const { mutate: approvePreApprovalMutate } = useMutation({
        mutationKey: ['approvePreApprovalMutation'],
        mutationFn: (studentId: string) => approveSubmittedPreApprovalCoord(axiosSecure, user.id, ""),
        onSuccess: () =>{
            queryClient.invalidateQueries(['getPreApprovalsCoord'])
        }
    })

    const { mutate: rejectPreApprovalMutate } = useMutation({
        mutationKey: ['rejectPreApprovalMutation'],
        mutationFn: (studentId: string) => rejectSubmittedPreApprovalCord(axiosSecure, user.id, ""),
        onSuccess: () =>{
            queryClient.invalidateQueries(['getPreApprovalsCoord'])
        }
    })

    const { mutate: downloadPreApprovalMutate } = useMutation({
        mutationKey: ['coordinatorDownloadPreApproval'],
        mutationFn: (studentId: string) => downloadForm(axiosSecure, studentId, Form.PRE_APPROVAL),
        onSuccess: (data) => downloadBlobFile(data, `${Form.PRE_APPROVAL}`)
    })

    const viewPreApproval = (formID: string) => {
        setSelectedPreApproval(preApprovals.find(element => element.formUuid === formID)?.preApprovalFormItems);
        setPreApprovalDetailsOpened(true);
    }
    const approvePreApproval = (formID: string) => {
        //Approve the selected student's wishlist
        approvePreApprovalMutate("") // TODO:

        // Close pop-up
        setPreApprovalDetailsOpened(false);
    }
    const rejectPreApproval = (formID: string) => {
        // Reject the selected student's wishlist
        rejectPreApprovalMutate("")

        // Close pop-up
        setRejectionFeedbackOpened(true);
        setPreApprovalDetailsOpened(false);
    }

    // Below are mock data, they will be changed.
    //------------------------------------------ Mock Data Starts ----------------------------------------------------------------

    // const preApprovalList = [
    //     { courseCode: "X_400614", courseName: "Data Structures and Algorithms", ectsCredit: "6.0", bilkentCourseInfo: "CS473 - Algorithms I", bilkentCredits: "3", electiveEquivalent: "" },
    //     { courseCode: "L_GCBAALG003", courseName: "Imagining the Dutch: themes in Dutch History", ectsCredit: "6.0", bilkentCourseInfo: "Arts Core Elective", bilkentCredits: "3", electiveEquivalent: "" },
    //     { courseCode: "L_AABAALG056", courseName: "Amsterdam: A Historical Introduction", ectsCredit: "6.0", bilkentCourseInfo: "General Elective", bilkentCredits: "3", electiveEquivalent: "" },
    //     { courseCode: "X_405067", courseName: "Operating Systems", ectsCredit: "6.0", bilkentCourseInfo: "CS342 - Operating Systems", bilkentCredits: "4", electiveEquivalent: "" },
    //     { courseCode: "X_401020", courseName: "Statistical Methods", ectsCredit: "6.0", bilkentCourseInfo: "Technical Elective", bilkentCredits: "3", electiveEquivalent: "MATH 260" },

    // ];
    //------------------------------------------ Mock Data Ends ----------------------------------------------------------------

    const preApprovalRows = getPreApprovalRows();
    const pendingApprovalRows = getPendingApprovalRows();

    const changeFilter = (filter: string | null, query: string) => {
        setSelectedApprovalStatusFilter(filter);
        searchPreApproval(query, filter);
    }

    const changeSearch = (value: string, filter: string | null) => {
        setSearchPreAppInput(value);
        searchPreApproval(value, filter);
    }
    const searchPreApproval = (query: string, filter: string | null) => {
        if (query !== '') {
            if (filter === null) {
                setTempPendingApprovalList(preApprovals.filter((student) => student.studentName.toLowerCase().includes(query.toLowerCase()) || student.studentID.toString().includes(query) || student.rejectionFeedback.toLowerCase().includes(query.toLowerCase())));
            }
            else {
                setTempPendingApprovalList(preApprovals.filter((student) => (student.studentName.toLowerCase().includes(query.toLowerCase()) || student.studentID.toString().includes(query) || student.rejectionFeedback.toLowerCase().includes(query.toLowerCase())) && student.status === filter));
            }
        }
        else {
            if (filter === null) {
                setTempPendingApprovalList(preApprovals);
            } else {
                setTempPendingApprovalList(preApprovals.filter((student) => student.status === filter));
            }

        }
    }

    const downloadPreApproval = (studentId: string) => {
        downloadPreApprovalMutate(studentId)
    }

    return (

        <>
            <RejectionFeedbackModal 
                rejectionFeedbackOpened={rejectionFeedbackOpened} 
                rejectionFeedback={rejectionFeedback} 
                setRejectionFeedbackOpened={setRejectionFeedbackOpened} 
                setRejectionFeedback={setRejectionFeedback}
            />
            <Modal
                opened={preApprovalDetailsOpened}
                size={"auto"}
                centered={true}
                onClose={() => setPreApprovalDetailsOpened(false)}
            >
                <Flex direction="column">
                    <Flex direction="row" gap="xs">
                        <Text fw={700}>Student Name:</Text>
                         <Text> {selectedStudentName}</Text>
                    </Flex>
                    <Flex direction="row" gap="xs">
                        <Text fw={700}>Student ID:</Text>
                        <Text> {selectedStudentID}</Text>
                    </Flex>
                </Flex>
                <Center>
                    <Table striped withBorder withColumnBorders>
                        <thead>
                            <tr>
                                <th></th>
                                <th>Course Code (Host)</th>
                                <th>Course Name (Host)</th>
                                <th>ECTS Credits</th>
                                <th>Course Code and Name for a Required Course,
                                    Elective Group Name for an Elective Requirement (Bilkent)
                                </th>
                                <th>Credits (Bilkent)</th>
                                <th>Elective Requirement Exemptions only: Course code(s) of directly equivalent course(s), if any (Bilkent)</th>
                            </tr>
                        </thead>
                        <tbody>{preApprovalRows}</tbody>
                    </Table>
                </Center>
                <Space h="xs"></Space>
                <Group position="right">
                    {showApprove && <Button color={'green'} onClick={() => { approvePreApproval(selectedStudentID) }}>Approve</Button>}
                    {showReject && <Button color={'red'} onClick={() => { rejectPreApproval(selectedStudentID) }}>Reject</Button>}
                </Group>
            </Modal>
            <Flex direction="column" gap={"sm"}>
                <Flex direction="row" gap={"xl"}>
                    <TextInput 
                        placeholder="Seach by name or ID" 
                        value={searchPreAppInput} 
                        onChange={(event) => changeSearch(event.currentTarget.value, selectedApprovalStatusFilter)}
                        />
                    <Select 
                        placeholder="Filter by status" 
                        value={selectedApprovalStatusFilter} 
                        onChange={(value) => { changeFilter(value, searchPreAppInput) }} 
                        clearable 
                        allowDeselect 
                        data={["Approved", "Pending", "Rejected"]}
                    />

                </Flex>
                <Table striped withBorder withColumnBorders>
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Student ID</th>
                            <th>View PreApproval</th>
                            <th>Download PreApproval</th>
                            <th>Rejection Feedback</th>
                        </tr>
                    </thead>
                    <tbody>{pendingApprovalRows}</tbody>
                </Table>
            </Flex>
        </>
    );

    function getPendingApprovalRows() {
        return tempPendingApprovalList.map((element) => (
            <>
                <tr key={element.studentName}>
                    <td>{element.studentName}</td>
                    <td>{element.studentID}</td>
                    <td>{""}
                        <Center>
                            <Button sx={{ width: "99%" }} 
                                leftIcon={element.status == "Approved" ? <IconCheck /> : element.status == "Pending" ? <IconSearch /> : <IconX />} 
                                color={element.status == "Approved" ? "green" : element.status == "Pending" ? "blue" : "red"} 
                                onClick={() => {
                                    setSelectedStudentName(element.studentName);
                                    setSelectedStudentID(element.studentID);
                                    viewPreApproval(element.formUuid);
                                    setShowApprove(element.status == "Pending" || element.status == "Rejected");
                                    setShowReject(element.status == "Approved" || element.status == "Pending");
                                }}
                            >
                                {(element.status == "Approved") || (element.status == "Rejected") ? "Change Decision" : "View"}
                            </Button>
                        </Center>
                    </td>
                    <td>
                        <Center>
                            <Button onClick={(e) => downloadPreApproval(element.studentID) }>
                                Download
                            </Button>    
                        </Center>
                    </td>
                    <td>
                        {element.rejectionFeedback}
                    </td>
                </tr>
            </>
        ));
    }

    function getPreApprovalRows() {
        return selectedPreApproval?.map((element) => (
            <tr key={element.courseCode}>
                <td>{selectedPreApproval.indexOf(element) + 1}</td>
                <td>{element.courseCode}</td>
                <td>{element.courseName}</td>
                <td>{element.ectsCredits}</td>
                <td>{element.bilkentCourseInfo}</td>
                <td>{element.bilkentCredit}</td>
                <td>{element.electiveEquivalent}</td>
            </tr>
        ));
    }
}
export default ApprovePreApprovalsTable;