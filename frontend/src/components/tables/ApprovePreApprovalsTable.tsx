import { Button, Table, Center, Modal, Group, Space } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useState } from "react";

const ApprovePreApprovalsTable = () => {
    const [opened, setOpened] = useState(false);
    const [selectedStudentName, setSelectedStudentName] = useState("");
    const [selectedStudentID, setSelectedStudentID] = useState(0);
    const [selectedIsApproved, setSelectedIsApproved] = useState(false);
    // const [preApprovalList, setPreApprovalList] = useState([]);
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
    const waitingApprovalList = [
        { studentName: "Can Ersoy", studentID: 22003216, isApproved: 0 },
        { studentName: "Selim Can Güler", studentID: 22002811, isApproved: 1 },
        { studentName: "Selim Can Güler", studentID: 22002811, isApproved: 2 },
        { studentName: "Selim Can Güler", studentID: 22002811, isApproved: 0 },
        { studentName: "Selim Can Güler", studentID: 22002811, isApproved: 0 },
        { studentName: "Selim Can Güler", studentID: 22002811, isApproved: 0 },
        { studentName: "Selim Can Güler", studentID: 22002811, isApproved: 0 },
        { studentName: "Selim Can Güler", studentID: 22002811, isApproved: 0 },
    ];

    const preApprovalList = [
        { courseCode: "X_400614", courseName: "Data Structures and Algorithms", ectsCredit: "6.0", bilkentCourseInfo: "CS473 - Algorithms I", bilkentCredits: "3", electiveEquivalent: "" },
        { courseCode: "L_GCBAALG003", courseName: "Imagining the Dutch: themes in Dutch History", ectsCredit: "6.0", bilkentCourseInfo: "Arts Core Elective", bilkentCredits: "3", electiveEquivalent: "" },
        { courseCode: "L_AABAALG056", courseName: "Amsterdam: A Historical Introduction", ectsCredit: "6.0", bilkentCourseInfo: "General Elective", bilkentCredits: "3", electiveEquivalent: "" },
        { courseCode: "X_405067", courseName: "Operating Systems", ectsCredit: "6.0", bilkentCourseInfo: "CS342 - Operating Systems", bilkentCredits: "4", electiveEquivalent: "" },
        { courseCode: "X_401020", courseName: "Statistical Methods", ectsCredit: "6.0", bilkentCourseInfo: "Technical Elective", bilkentCredits: "3", electiveEquivalent: "MATH 260" },

    ];
    //------------------------------------------ Mock Data Ends ----------------------------------------------------------------

    const preApprovalRows = preApprovalList.map((element) => (
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
    const waitingApprovalRows = waitingApprovalList.map((element) => (
        <tr key={element.studentName}>
            <td>{element.studentName}</td>
            <td>{element.studentID}</td>
            <td>{""}
                <Center><Button leftIcon={element.isApproved == 0 ? <IconCheck /> : element.isApproved == 1 ? <IconSearch /> : <IconX />} color={element.isApproved == 0 ? "green" : element.isApproved == 1 ? "blue" : "red"} onClick={() => {
                    setSelectedStudentName(element.studentName);
                    setSelectedStudentID(element.studentID);
                    viewPreApproval(element.studentID);
                    setSelectedIsApproved(element.isApproved == 1 || element.isApproved == 2);
                }}>View</Button></Center></td>
        </tr>
    ));


    return (
        <><Modal
            opened={opened}
            size={"auto"}
            centered={true}
            onClose={() => setOpened(false)}
            title={"Student Name: " + selectedStudentName + "   Student ID: " + selectedStudentID}
        >
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
                {selectedIsApproved && <Button color={'green'} onClick={() => { approvePreApproval(selectedStudentID) }}>Approve</Button>}
                <Button color={'red'} onClick={() => { rejectPreApproval(selectedStudentID) }}>Reject</Button>
            </Group>
        </Modal>
            <Table striped withBorder withColumnBorders>
                <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Student ID</th>
                        <th>View PreApproval</th>
                    </tr>
                </thead>
                <tbody>{waitingApprovalRows}</tbody>

            </Table></>);
}
export default ApprovePreApprovalsTable;