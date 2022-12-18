import { Button, Center, Group, Modal, Space, Table } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { toast } from "react-toastify";
import { approveWishlist, getStudentWishlist, rejectWishlist } from '../../api/Coordinator/CourseWishlistService';
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useRejectWishlist } from "../../hooks/useRejectWishlist";
import { useUser } from "../../provider/UserProvider";
import { Course, ExistingCourseWishlist, StudentAssociatedCourse, StudentAssociatedWishlist, WishlistItemsInterface } from "../../types";

interface ApproveWishlistTableProps {
    wishlists: Array<ExistingCourseWishlist>
}

const ApproveWishlistsTable = ({ wishlists }: ApproveWishlistTableProps) => {
    const axiosSecure = useAxiosSecure();
    const { user } = useUser();
    const [opened, setOpened] = useState(false);
    const [selectedStudentID, setSelectedStudentID] = useState("");
    const [selectedIsApproved, setSelectedIsApproved] = useState(false);
    const [wishlistDetails, setWishlistDetails] = useState<Array<WishlistItemsInterface>>();
    const queryClient = useQueryClient()

    const { mutate: mutateGetWishlist, data:dataWishlistDetails } = useMutation({
        mutationKey: ['getWishlist'],
        mutationFn: (studentBilkentId: string) => getStudentWishlist(axiosSecure, user!.id, studentBilkentId),
        onSuccess: (dataWishlistDetails) => {
            
            setWishlistDetails(dataWishlistDetails.data.items);
        }
    })

    const { mutate: mutateApproval } = useMutation({
        mutationKey: ['approveWishlist'],
        mutationFn: (studentBilkentId: string) => approveWishlist(axiosSecure, user!.id, studentBilkentId),
        onSuccess:()=>{
            queryClient.invalidateQueries(['getAllStudentWishlists']);
            toast.success(`Wishlist approved successfully.`);
        },
        onError:()=>{
            toast.error(`Wishlist approve failed.`);
        }
    })

    const { mutate: mutateRejection } = useMutation({
        mutationKey: ['rejectWishlist'],
        mutationFn: (studentBilkentId: string) => rejectWishlist(axiosSecure, user!.id, studentBilkentId),
        onSuccess:()=>{
            queryClient.invalidateQueries(['getAllStudentWishlists']);
            toast.success(`Wishlist rejected successfully.`);
        },
        onError:()=>{
            toast.error(`Wishlist reject failed.`);
        }
    })

    const viewWishlist = (studentBilkentId: string) => {
        //TODO: send data to api, get student's wishlist
        mutateGetWishlist(studentBilkentId);
        setOpened(true);

    }

    const approve = (studentBilkentId: string) => {
        // Approve the selected student's wishlist
        mutateApproval(studentBilkentId);
        // Close pop-up
        setOpened(false);
    }
    const reject = (studentBilkentId: string) => {
        // Reject the selected student's wishlist
        mutateRejection(studentBilkentId)
        // Close pop-up
        setOpened(false);
    }

    const wishlistRows = wishlistDetails?.map((element) => (
        <tr key={element.mappings[0].hostCourse}>
            <td>{element.mappings[0].hostCourse}</td>
            <td>{element.mappings[0].hostName}</td>
            <td>{element.ects}</td>
            <td>{element.bilkentCourse}</td>
            <td>{element.bilkentName}</td>
            <td>{element.ects}</td>
        </tr>
    ));
    const waitingApprovalRows = wishlists.map((wishlist) => (
        <tr key={wishlist.studentId}>
            <td>{wishlist.studentId}</td>
            <td>{""}
                <Center>
                    <Button
                        leftIcon={wishlist.status == 'APPROVED' ? <IconCheck /> : wishlist.status == 'PENDING' ? <IconSearch /> : <IconX />}
                        color={wishlist.status == 'APPROVED' ? "green" : wishlist.status == 'PENDING' ? "blue" : "red"}
                        onClick={() => {
                            setSelectedStudentID(wishlist.studentId);
                            viewWishlist(wishlist.studentId);
                            setSelectedIsApproved(wishlist.status == "PENDING" || wishlist.status == "REJECTED");
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
            size={"auto"}
            opened={opened}
            centered={true}
            onClose={() => setOpened(false)}
            title={"Student ID: " + selectedStudentID}
        >
            {<Table striped withBorder withColumnBorders>
                <thead>
                    <tr>
                        <th>Course Code At Host University</th>
                        <th>Course Name At Host University</th>
                        <th>ECTS Credits of Host Course</th>
                        <th>Course Code at Bilkent</th>
                        <th>Course Name at Bilkent</th>
                        <th>ECTS Credits of Bilkent Course</th>
                    </tr>
                </thead>
                <tbody>{wishlistRows}</tbody>

            </Table>}
            <Space h="xs" />
            <Group position={"right"}>
                {selectedIsApproved && <Button color={'green'} onClick={() => { approve(selectedStudentID) }}>Approve</Button>}
                <Button color={'red'} onClick={() => { reject(selectedStudentID) }}>Reject</Button>
            </Group>
        </Modal>
            <Table striped withBorder withColumnBorders>
                <thead>
                    <tr>
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