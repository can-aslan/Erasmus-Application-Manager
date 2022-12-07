import { Button, Center, Group, Modal, Space, Table } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useState } from "react";
import { useApproveWishlist } from "../../hooks/useApproveWishlist";
import { useRejectWishlist } from "../../hooks/useRejectWishlist";
import { StudentAssociatedCourse, StudentAssociatedWishlist, WishlistItemType } from "../../types";

interface ApproveWishlistTableProps {
    wishlists: Array<StudentAssociatedCourse>
}

const ApproveWishlistsTable = ({wishlists}: ApproveWishlistTableProps) => {
    const [opened, setOpened] = useState(false);
    const [selectedStudentName, setSelectedStudentName] = useState("");
    const [selectedStudentID, setSelectedStudentID] = useState("");
    const [selectedWishlistId, setSelectedWishlistId] = useState("")
    const [selectedIsApproved, setSelectedIsApproved] = useState(false);

    const { mutate: mutateApproval, isLoading: isApprovalLoading, isError: isApprovalError } = useApproveWishlist()
    const { mutate: mutateRejection, isLoading: isRejectionLoading, isError: isRejectionError } = useRejectWishlist()


    const viewWishlist = (wishlistId: string) => {
        //TODO: send data to api, get student's wishlist
        setOpened(true);

    }
    const approveWishlist = (wishlistId: string) => {
        // Approve the selected student's wishlist
        // Close pop-up
        mutateApproval(wishlistId)
        setOpened(false);
    }
    const rejectWishlist = (wishlistId: string) => {
        // Reject the selected student's wishlist
        mutateRejection(wishlistId)

        // Close pop-up
        setOpened(false);
    }

    // Below are mock data, they will be changed.
    //------------------------------------------ Mock Data Starts ----------------------------------------------------------------
    const wishlistList: WishlistItemType[] = [
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, uuid: "xxx"},
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, uuid: "abc"},
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, uuid: "abd"},
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, uuid: "aby"},
    ];
    const waitingWishlist: StudentAssociatedWishlist[] = [
        { studentName: "Can Ersoy", studentId: "22003216", status: 'approved', wishlistItems: wishlistList, wishlistUuid: 'x'},
        { studentName: "Can Ersoy", studentId: "22003216", status: 'approved', wishlistItems: wishlistList, wishlistUuid: 'x'},
        { studentName: "Can Ersoy", studentId: "22003216", status: 'approved', wishlistItems: wishlistList, wishlistUuid: 'x'},
        { studentName: "Can Ersoy", studentId: "22003216", status: 'approved', wishlistItems: wishlistList, wishlistUuid: 'x'},
    ];

    //------------------------------------------ Mock Data Ends ----------------------------------------------------------------

    const wishlistRows = wishlistList.map((element) => (
        <tr key={element.courseCode}>
            <td>{element.courseCode}</td>
            <td>{element.courseName}</td>
            <td>{element.ECTSCredits}</td>
            <td>{""}
                <Center>
                    <Button 
                        onClick={() => {
                            //TODO: download syllabus?
                        }}>
                        Syllabus
                    </Button>
                </Center></td>
        </tr>
    ));
    const waitingApprovalRows = waitingWishlist.map((element) => (
        <tr key={element.studentName}>
            <td>{element.studentName}</td>
            <td>{element.studentId}</td>
            <td>{""}
                <Center>
                    <Button 
                        leftIcon={element.status == 'approved' ? <IconCheck /> : element.status == 'pending' ? <IconSearch /> : <IconX />} 
                        color={element.status == 'approved' ? "green" : element.status == 'pending' ? "blue" : "red"} 
                        onClick={() => {
                            setSelectedStudentName(element.studentName);
                            setSelectedStudentID(element.studentId);
                            viewWishlist(element.wishlistUuid);
                            setSelectedIsApproved(element.status == "pending" || element.status == "rejected");
                        }}
                    >   
                        View
                    </Button>
                </Center>
            </td>
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
            <Space h="xs"/>
            <Group position={"right"}>
                {selectedIsApproved && <Button color={'green'} onClick={() => { approveWishlist(selectedWishlistId) }}>Approve</Button>}
                <Button color={'red'} onClick={() => { rejectWishlist(selectedWishlistId) }}>Reject</Button>
            </Group>
        </Modal>
            <Table striped withBorder withColumnBorders>
                <thead>
                    <tr>
                        <th>Student Name</th>
                        <th>Student ID</th>
                        <th>View Wishlist</th>
                    </tr>
                </thead>
                <tbody>
                    {waitingApprovalRows}
                </tbody>

            </Table></>);
}
export default ApproveWishlistsTable;