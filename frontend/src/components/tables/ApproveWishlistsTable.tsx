import { Button, Center, Group, Modal, Space, Table } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import { approveWishlist, rejectWishlist } from '../../api/Coordinator/CourseWishlistService';
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useRejectWishlist } from "../../hooks/useRejectWishlist";
import { useUser } from "../../provider/UserProvider";
import { Course, StudentAssociatedCourse, StudentAssociatedWishlist } from "../../types";

interface ApproveWishlistTableProps {
    wishlists: Array<StudentAssociatedCourse>
}

const ApproveWishlistsTable = ({wishlists}: ApproveWishlistTableProps) => {
    const axiosSecure = useAxiosSecure()
    const [opened, setOpened] = useState(false);
    const [selectedStudentName, setSelectedStudentName] = useState("");
    const [selectedStudentID, setSelectedStudentID] = useState("");
    const [selectedWishlistId, setSelectedWishlistId] = useState("")
    const [selectedIsApproved, setSelectedIsApproved] = useState(false);
    const { user } = useUser()

    const {mutate: mutateApproval} = useMutation({
        mutationKey: ['approveWishlist'],
        mutationFn: (wishlistId: string) => approveWishlist(axiosSecure, user!.id, wishlistId),
    })

    const { mutate: mutateRejection } = useMutation({
        mutationKey: ['rejectWishlist'],
        mutationFn: (wishlistId: string) => rejectWishlist(axiosSecure, user!.id, wishlistId)
    })



    const viewWishlist = (wishlistId: string) => {
        //TODO: send data to api, get student's wishlist
        setOpened(true);

    }
    const approve = (wishlistId: string) => {
        // Approve the selected student's wishlist
        mutateApproval(wishlistId)
        // Close pop-up
        setOpened(false);
    }
    const reject = (wishlistId: string) => {
        // Reject the selected student's wishlist
        mutateRejection(wishlistId)
        // Close pop-up
        setOpened(false);
    }

    // Below are mock data, they will be changed.
    //------------------------------------------ Mock Data Starts ----------------------------------------------------------------
    const wishlistList: Course[] = [
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, wishlistUuid: "xxx"},
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, wishlistUuid: "abc"},
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, wishlistUuid: "abd"},
        { courseCode: "CS 319", courseName: "Object Oriented Software Engineering", ECTSCredits: 6.5, bilkentCredits: 6.5, wishlistUuid: "aby"},
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
                            setSelectedWishlistId(element.wishlistUuid)
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
                {selectedIsApproved && <Button color={'green'} onClick={() => { approve(selectedWishlistId) }}>Approve</Button>}
                <Button color={'red'} onClick={() => { reject(selectedWishlistId) }}>Reject</Button>
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