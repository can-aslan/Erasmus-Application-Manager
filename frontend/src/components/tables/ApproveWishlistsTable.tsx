import { Button, Table, Center, Modal } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useState } from "react";

const ApproveWishlistsTable = () => {
    const [opened, setOpened] = useState(false);
    const [selectedStudentName, setSelectedStudentName] = useState("");
    const [selectedStudentID, setSelectedStudentID] = useState(0);
    const [selectedIsApproved, setSelectedIsApproved] = useState(false);
    const viewWishlist = (studentID: number) => {
        //TODO: send data to api, get student's wishlist
        console.log(studentID);
        setOpened(true);
    }
    const approveWishlist = (studentID: number) => {
        //TODO: send request to api, approve the selected student's wishlist
        // Close pop-up
        setOpened(false);
    }
    const rejectWishlist = (studentID: number) => {
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

    const wishlistList = [
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ectsCredit: "6.5", syllabus: "some link or pdf" },
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ectsCredit: "6.5", syllabus: "some link or pdf" },
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ectsCredit: "6.5", syllabus: "some link or pdf" },
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ectsCredit: "6.5", syllabus: "some link or pdf" },
    ];
    //------------------------------------------ Mock Data Ends ----------------------------------------------------------------

    const wishlistRows = wishlistList.map((element) => (
        <tr key={element.courseCode}>
            <td>{element.courseCode}</td>
            <td>{element.courseName}</td>
            <td>{element.ectsCredit}</td>
            <td>{""}
                <Center><Button onClick={() => {
                    //TODO: download syllabus?
                }}>Syllabus</Button></Center></td>
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
                    viewWishlist(element.studentID);
                    setSelectedIsApproved(element.isApproved == 1 || element.isApproved == 2);
                }}>View</Button></Center></td>
        </tr>
    ));


    return (
        <><Modal
            opened={opened}
            centered={true}
            onClose={() => setOpened(false)}
            title={"Student Name: " + selectedStudentName + "   Student ID: " + selectedStudentID}
        >
            {<Table striped withBorder withColumnBorders>
                <thead>
                    <tr>
                        <th>Course Code At Host University</th>
                        <th>Course Name At Host University</th>
                        <th>ECTS Credit</th>
                        <th>Download Syllabus</th>
                    </tr>
                </thead>
                <tbody>{wishlistRows}</tbody>

            </Table>}
            {selectedIsApproved && <Button color={'green'} onClick={() => { approveWishlist(selectedStudentID) }}>Approve</Button>}
            <Button color={'red'} onClick={() => { rejectWishlist(selectedStudentID) }}>Reject</Button>
        </Modal>
            <Table striped withBorder withColumnBorders>
                <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Student ID</th>
                        <th>View Wishlist</th>
                    </tr>
                </thead>
                <tbody>{waitingApprovalRows}</tbody>

            </Table></>);
}
export default ApproveWishlistsTable;