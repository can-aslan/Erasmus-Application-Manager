import { Button, Center, Flex, Group, Modal, Select, Space, Table, Text, TextInput } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import React, { useEffect, useState } from "react";
import { PreApprovalForm } from "../../types";

interface ApprovePreApprovalsTableProps {
    preApprovals: Array<PreApprovalForm>
}

const ApprovePreApprovalsTable = ({preApprovals}: ApprovePreApprovalsTableProps) => {
    const [opened, setOpened] = useState(false);
    const [selectedStudentName, setSelectedStudentName] = useState("");
    const [selectedStudentID, setSelectedStudentID] = useState(0);
    const [showReject, setShowReject] = useState(false);
    const [showApprove, setShowApprove] = useState(false);
    const [allPendingApprovalList, setPendingApprovalList] = useState([
        { studentName: "Can Ersoy", studentID: 22003216, status: "Approved" },
        { studentName: "Selim Can Güler", studentID: 22002811, status: "Pending" },
        { studentName: "a", studentID: 22002811, status: "Rejected" },
        { studentName: "b", studentID: 22002811, status: "Approved" },
        { studentName: "c", studentID: 22002811, status: "Rejected" },
        { studentName: "d", studentID: 22002811, status: "Pending" },
        { studentName: "e", studentID: 22002811, status: "Approved" },
        { studentName: "f", studentID: 22002811, status: "Rejected" },
    ])
    const [tempPendingApprovalList, setTempPendingApprovalList] = useState(allPendingApprovalList);
    const [selectedApprovalStatusFilter, setSelectedApprovalStatusFilter] = useState<string | null>(null);
    const [searchPreAppInput, setSearchPreAppInput] = useState('');

    const viewPreApproval = (studentID: number) => {
        //TODO: send data to api, get student's wishlist
        // setPreApprovalList(buraya apiden gelen isteğe göre list koy);
        setOpened(true);
    }
    const approvePreApproval = (studentID: number) => {
        //TODO: send request to api, approve the selected student's wishlist
        // Close pop-up
        setOpened(false);
    }
    const rejectPreApproval = (studentID: number) => {
        //TODO: send request to api, reject the selected student's wishlist
        // Close pop-up
        setOpened(false);
    }

    // Below are mock data, they will be changed.
    //------------------------------------------ Mock Data Starts ----------------------------------------------------------------

    const preApprovalList = [
        { courseCode: "X_400614", courseName: "Data Structures and Algorithms", ectsCredit: "6.0", bilkentCourseInfo: "CS473 - Algorithms I", bilkentCredits: "3", electiveEquivalent: "" },
        { courseCode: "L_GCBAALG003", courseName: "Imagining the Dutch: themes in Dutch History", ectsCredit: "6.0", bilkentCourseInfo: "Arts Core Elective", bilkentCredits: "3", electiveEquivalent: "" },
        { courseCode: "L_AABAALG056", courseName: "Amsterdam: A Historical Introduction", ectsCredit: "6.0", bilkentCourseInfo: "General Elective", bilkentCredits: "3", electiveEquivalent: "" },
        { courseCode: "X_405067", courseName: "Operating Systems", ectsCredit: "6.0", bilkentCourseInfo: "CS342 - Operating Systems", bilkentCredits: "4", electiveEquivalent: "" },
        { courseCode: "X_401020", courseName: "Statistical Methods", ectsCredit: "6.0", bilkentCourseInfo: "Technical Elective", bilkentCredits: "3", electiveEquivalent: "MATH 260" },

    ];
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
                setTempPendingApprovalList(allPendingApprovalList.filter((student) => student.studentName.toLowerCase().includes(query.toLowerCase()) || student.studentID.toString().includes(query)));
            }
            else {
                setTempPendingApprovalList(allPendingApprovalList.filter((student) => (student.studentName.toLowerCase().includes(query.toLowerCase()) || student.studentID.toString().includes(query)) && student.status === filter));
            }
        }
        else {
            if (filter === null) {
                setTempPendingApprovalList(allPendingApprovalList);
            } else {
                setTempPendingApprovalList(allPendingApprovalList.filter((student) => student.status === filter));
            }

        }
    }

    return (
        <><Modal
            opened={opened}
            size={"auto"}
            centered={true}
            onClose={() => setOpened(false)}
        >
            <Flex direction="column">
                <Flex direction="row" gap="xs"><Text fw={700}>Student Name:</Text> <Text> {selectedStudentName}</Text></Flex>
                <Flex direction="row" gap="xs"><Text fw={700}>Student ID:</Text> <Text> {selectedStudentID}</Text></Flex>
            </Flex>

            <Center><Table striped withBorder withColumnBorders>

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

            </Table></Center>
            <Space h="xs"></Space>
            <Group position="right">
                {showApprove && <Button color={'green'} onClick={() => { approvePreApproval(selectedStudentID) }}>Approve</Button>}
                {showReject && <Button color={'red'} onClick={() => { rejectPreApproval(selectedStudentID) }}>Reject</Button>}
            </Group>
        </Modal>
            <Flex direction="column" gap={"sm"}>
                <Flex direction="row" gap={"xl"}>
                    <TextInput placeholder="Seach by name or ID" value={searchPreAppInput} onChange={(event) => changeSearch(event.currentTarget.value, selectedApprovalStatusFilter)}></TextInput><Select value={selectedApprovalStatusFilter} onChange={(value) => { changeFilter(value, searchPreAppInput) }} clearable allowDeselect data={["Approved", "Pending", "Rejected"]}></Select>
                </Flex>
                <Table striped withBorder withColumnBorders>
                    <thead>
                        <tr>
                            <th>Student Name</th>
                            <th>Student ID</th>
                            <th>View PreApproval</th>
                        </tr>
                    </thead>
                    <tbody>{pendingApprovalRows}</tbody>
                </Table>
            </Flex></>);

    function getPendingApprovalRows() {
        return tempPendingApprovalList.map((element) => (
            <tr key={element.studentName}>
                <td>{element.studentName}</td>
                <td>{element.studentID}</td>
                <td>{""}
                    <Center><Button sx={{ width: "99%" }} leftIcon={element.status == "Approved" ? <IconCheck /> : element.status == "Pending" ? <IconSearch /> : <IconX />} color={element.status == "Approved" ? "green" : element.status == "Pending" ? "blue" : "red"} onClick={() => {
                        setSelectedStudentName(element.studentName);
                        setSelectedStudentID(element.studentID);
                        viewPreApproval(element.studentID);
                        setShowApprove(element.status == "Pending" || element.status == "Rejected");
                        setShowReject(element.status == "Approved" || element.status == "Pending");
                    }}>{(element.status == "Approved") || (element.status == "Rejected") ? "Change Decision" : "View"}</Button></Center></td>
            </tr>
        ));
    }

    function getPreApprovalRows() {
        return preApprovalList.map((element) => (
            <tr key={element.courseCode}>
                <td>{preApprovalList.indexOf(element) + 1}</td>
                <td>{element.courseCode}</td>
                <td>{element.courseName}</td>
                <td>{element.ectsCredit}</td>
                <td>{element.bilkentCourseInfo}</td>
                <td>{element.bilkentCredits}</td>
                <td>{element.electiveEquivalent}</td>
            </tr>
        ));
    }
}
export default ApprovePreApprovalsTable;