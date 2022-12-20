import { Button, Center, Flex, Group, Modal, Select, Space, Table, Text, TextInput } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
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

    const [rejectionFeedbackOpened, setRejectionFeedbackOpened] = useState(false);

    const [rejectionFeedback, setRejectionFeedback] = useState("");
    
    const [tempPendingApprovalList, setTempPendingApprovalList] = useState(preApprovals);
    const [selectedApprovalStatusFilter, setSelectedApprovalStatusFilter] = useState<string | null>(null);
    const [searchPreAppInput, setSearchPreAppInput] = useState('');

    const { mutate: approvePreApprovalMutate, isLoading: isApproveLoading } = useMutation({
        mutationKey: ['approvePreApprovalMutation'],
        mutationFn: (studentId: string) => approveSubmittedPreApprovalCoord(axiosSecure, user.id, studentId),
        onSuccess: () =>{
            toast.success("Approved the pre-approval file successfully.")
            queryClient.invalidateQueries(['getPreApprovalsCoord'])
        },
        onError: () => toast.error("Please check if you have uploaded your signature to approve this file.")
    })

    const { mutate: rejectPreApprovalMutate, isLoading: isRejectLoading  } = useMutation({
        mutationKey: ['rejectPreApprovalMutation'],
        mutationFn: (studentId: string) => rejectSubmittedPreApprovalCord(axiosSecure, user.id, studentId),
        onSuccess: () =>{
            toast.success("Rejected the pre-approval file successfully.")
            queryClient.invalidateQueries(['getPreApprovalsCoord'])
        }
    })

    const { mutate: downloadPreApprovalMutate, isLoading: isPreApprovalDownloadLoading } = useMutation({
        mutationKey: ['coordinatorDownloadPreApproval'],
        mutationFn: (studentId: string) => downloadForm(axiosSecure, studentId, Form.PRE_APPROVAL),
        onSuccess: (data) => downloadBlobFile(data, `${Form.PRE_APPROVAL}`)
    })

    const approvePreApproval = (bilkentId: string) => {
        // Approve the selected student's wishlist
        approvePreApprovalMutate(bilkentId);

        // Close pop-up
    }
    const rejectPreApproval = (bilkentId: string) => {
        // Reject the selected student's wishlist
        rejectPreApprovalMutate(bilkentId);

        // Close pop-up
        setRejectionFeedbackOpened(false);
    }
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
                setTempPendingApprovalList(preApprovals.filter((student) => (student.student.user.name + " " + student.student.user.surname).toLowerCase().includes(query.toLowerCase()) || student.student.user.bilkentId.toString().includes(query)));
            }
            else {
                setTempPendingApprovalList(preApprovals.filter((student) => ((student.student.user.name + " " + student.student.user.surname).toLowerCase().includes(query.toLowerCase()) || student.student.user.bilkentId.toString().includes(query)) && student.preApprovalStatus === filter));
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
                            <th>Approve</th>
                            <th>Reject</th>
                            <th>Download</th>
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
                    {element.preApprovalStatus === "COORDINATOR_APPROVED" ? 
                    <>
                        <td> 
                            <Text color='green'>Approved</Text>
                        </td>
                        <td> 
                            <Button 
                                color={'red'} 
                                onClick={() => { setRejectionFeedbackOpened(true); }}
                                loading={isRejectLoading}
                            >
                                    Reject
                            </Button>

                        </td>
                    </> : element.preApprovalStatus === "COORDINATOR_REJECTED" ?
                            <>
                                <td> 
                                    <Button 
                                        color={'green'} 
                                        onClick={() => { approvePreApproval(element.student.user.bilkentId) }}
                                        loading={isApproveLoading}
                                    >
                                        Approve
                                    </Button>
                                </td>
                                <td> 

                                    <Text color='red'>Rejected</Text>
                                </td>
                            </> : (
                                <>
                                    <td> 
                                        <Button 
                                            color={'green'}
                                            onClick={() => { approvePreApproval(element.student.user.bilkentId) }}
                                            loading={isApproveLoading}
                                        >
                                            Approve
                                        </Button>
                                    </td>
                                    <td> 
                                        <Button 
                                            color={'red'} 
                                            onClick={() => { rejectPreApproval(element.student.user.bilkentId)}}
                                            loading={isRejectLoading}
                                        >
                                            Reject
                                        </Button>
                                    </td>
                                </>
                            )
                    }
                    <td>

                        <Center>
                            <Button 
                                onClick={(e) => downloadPreApproval(element.student.user.id) }
                                loading={isPreApprovalDownloadLoading}
                            >
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