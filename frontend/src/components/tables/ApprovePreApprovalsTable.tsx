import { Button, Center, Flex, Group, Modal, Select, Space, Table, Text, TextInput } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import React, { useEffect, useState } from "react";
import { approveSubmittedPreApprovalCoord, rejectSubmittedPreApprovalCord } from "../../api/Coordinator/PreapprovalService";
import { downloadForm } from "../../api/FileService";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useUser } from "../../provider/UserProvider";
import { Form, PreApprovalForm, WishlistItemsInterface } from "../../types";
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
    const [selectedPreApproval, setSelectedPreApproval] = useState<PreApprovalForm>();

    const { mutate: approvePreApprovalMutate } = useMutation({
        mutationKey: ['approvePreApprovalMutation'],
        mutationFn: (studentId: string) => approveSubmittedPreApprovalCoord(axiosSecure, user.id, studentId),
        onSuccess: () =>{
            queryClient.invalidateQueries(['getPreApprovalsCoord'])
        }
    })

    const { mutate: rejectPreApprovalMutate } = useMutation({
        mutationKey: ['rejectPreApprovalMutation'],
        mutationFn: (studentId: string) => rejectSubmittedPreApprovalCord(axiosSecure, user.id, studentId),
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
        setSelectedPreApproval(preApprovals.find(element => element.id === formID));
        setPreApprovalDetailsOpened(true);
    }
    const approvePreApproval = () => {
        // Approve the selected student's wishlist
        approvePreApprovalMutate(selectedStudentID);

        // Close pop-up
        setPreApprovalDetailsOpened(false);
    }
    const rejectPreApproval = () => {
        // Reject the selected student's wishlist
        rejectPreApprovalMutate(selectedStudentID);

        // Close pop-up
        setRejectionFeedbackOpened(true);
        setPreApprovalDetailsOpened(false);
    }
    function getPreApprovalRows() {
        return selectedPreApproval?.wishlist?.mappings?.map((element) => (
            <tr key={element.hostCourse}>
                <td>{selectedPreApproval.wishlist.mappings.indexOf(element) + 1}</td>
                <td>{element.hostCourse}</td>
                <td>{element.hostName}</td>
                <td>{element.ects}</td>
                <td>{selectedPreApproval.wishlist.bilkentCourse + selectedPreApproval.wishlist.bilkentName}</td>
                <td>{selectedPreApproval.wishlist.bilkentCredits}</td>
            </tr>
        ));
    }
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
                setTempPendingApprovalList(preApprovals.filter((student) => (student.student.user.name + student.student.user.surname).toLowerCase().includes(query.toLowerCase()) || student.student.user.bilkentId.toString().includes(query)));
            }
            else {
                setTempPendingApprovalList(preApprovals.filter((student) => ((student.student.user.name + student.student.user.surname).toLowerCase().includes(query.toLowerCase()) || student.student.user.bilkentId.toString().includes(query)) && student.preApprovalStatus === filter));
            }
        }
        else {
            if (filter === null) {
                setTempPendingApprovalList(preApprovals);
            } else {
                setTempPendingApprovalList(preApprovals.filter((student) => student.preApprovalStatus === filter));
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
                    {showApprove && <Button color={'green'} onClick={() => { approvePreApproval() }}>Approve</Button>}
                    {showReject && <Button color={'red'} onClick={() => { rejectPreApproval() }}>Reject</Button>}
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
                        data={["COORDINATOR_APPROVED", "PENDING", "COORDINATOR_REJECTED"]}
                    />

                </Flex>
                <Table striped withBorder withColumnBorders>
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Student ID</th>
                            <th>View PreApproval</th>
                            <th>Download PreApproval</th>
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
                <tr key={element.student.user.id}>
                    <td>{element.student.user.name + " " + element.student.user.surname}</td>
                    <td>{element.student.user.bilkentId}</td>
                    <td>{""}
                        <Center>
                            <Button sx={{ width: "99%" }} 
                                leftIcon={element.preApprovalStatus == "COORDINATOR_APPROVED" ? <IconCheck /> : element.preApprovalStatus == "PENDING" ? <IconSearch /> : <IconX />} 
                                color={element.preApprovalStatus == "COORDINATOR_APPROVED" ? "green" : element.preApprovalStatus == "PENDING" ? "blue" : "red"} 
                                onClick={() => {
                                    setSelectedStudentName(element.student.user.name + " " + element.student.user.surname);
                                    setSelectedStudentID(element.student.user.bilkentId);
                                    viewPreApproval(element.id);
                                    setShowApprove(element.preApprovalStatus == "PENDING" || element.preApprovalStatus == "COORDINATOR_REJECTED");
                                    setShowReject(element.preApprovalStatus == "COORDINATOR_APPROVED" || element.preApprovalStatus == "PENDING");
                                }}
                            >
                                {(element.preApprovalStatus == "COORDINATOR_APPROVED") || (element.preApprovalStatus == "COORDINATOR_REJECTED") ? "Change Decision" : "View"}
                            </Button>
                        </Center>
                    </td>
                    <td>
                        <Center>
                            <Button onClick={(e) => downloadPreApproval(element.student.user.bilkentId) }>
                                Download
                            </Button>    
                        </Center>
                    </td>
                </tr>
            </>
        ));
    }
}
export default ApprovePreApprovalsTable;