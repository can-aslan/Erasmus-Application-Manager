import { Button, Center, Flex, Group, Modal, Space, Table, Text, TextInput } from "@mantine/core";
import { IconCheck, IconSearch, IconX } from "@tabler/icons";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { SetStateAction, useState } from "react";
import { toast } from "react-toastify";
import { changeStatus, getStudentWishlist } from '../../api/Coordinator/CourseWishlistService';
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useRejectWishlist } from "../../hooks/useRejectWishlist";
import { useUser } from "../../provider/UserProvider";
import { CoordinatorWishlistChange, Course, ExistingCourseWishlist, StudentAssociatedCourse, StudentAssociatedWishlist, WishlistItemsInterface } from "../../types";
import RejectionFeedbackModal from "../modals/RejectionFeedbackModal";

interface ApproveWishlistTableProps {
    wishlists: Array<ExistingCourseWishlist>
}

const ApproveWishlistsTable = ({ wishlists }: ApproveWishlistTableProps) => {
    const axiosSecure = useAxiosSecure();
    const { user } = useUser();
    const [rejectionFeedback, setRejectionFeedback] = useState("");
    const [rejectionFeedbackOpened, setRejectionFeedbackOpened] = useState(false);
    const [opened, setOpened] = useState(false);
    const [selectedStudentID, setSelectedStudentID] = useState("");
    const [selectedShowApprove, setSelectedShowApprove] = useState(false);
    const [selectedShowReject, setSelectedShowReject] = useState(false);
    const [wishlistDetails, setWishlistDetails] = useState<Array<WishlistItemsInterface>>();
    const queryClient = useQueryClient()

    const { mutate: mutateGetWishlist, data: dataWishlistDetails } = useMutation({
        mutationKey: ['getWishlist'],
        mutationFn: (studentBilkentId: string) => getStudentWishlist(axiosSecure, user!.id, studentBilkentId),
        onSuccess: (dataWishlistDetails) => {

            setWishlistDetails(dataWishlistDetails.data.items);
        }
    })

    const { mutate: mutateApproval } = useMutation({
        mutationKey: ['approveWishlist'],
        mutationFn: (body: CoordinatorWishlistChange) => changeStatus(axiosSecure, body),
        onSuccess: () => {
            queryClient.invalidateQueries(['getAllStudentWishlists']);
            toast.success(`Wishlist approved successfully.`);
        },
        onError: () => {
            toast.error(`Wishlist approve failed.`);
        }
    })

    const { mutate: mutateRejection } = useMutation({
        mutationKey: ['rejectWishlist'],
        mutationFn: (body: CoordinatorWishlistChange) => changeStatus(axiosSecure, body),
        onSuccess: () => {
            queryClient.invalidateQueries(['getAllStudentWishlists']);
            toast.success(`Wishlist rejected successfully.`);
        },
        onError: () => {
            toast.error(`Wishlist reject failed.`);
        }
    })

    const viewWishlist = (studentBilkentId: string) => {
        //TODO: send data to api, get student's wishlist
        mutateGetWishlist(studentBilkentId);
        setOpened(true);

    }

    const approve = (studentBilkentId: string, newStatus: string) => {
        // Approve the selected student's wishlist
        const approveBody: CoordinatorWishlistChange = {
            status: newStatus,
            studentBilkentId: studentBilkentId,
            coordinatorUserId: user.id,
        }
        mutateApproval(approveBody);
        // Close pop-up
        setOpened(false);
    }
    const reject = (studentBilkentId: string, newStatus: string, feedback: string) => {
        // Reject the selected student's wishlist
        const rejectionBody: CoordinatorWishlistChange = {
            status: newStatus,
            studentBilkentId: studentBilkentId,
            coordinatorUserId: user.id,
            feedback: feedback
        }
        mutateRejection(rejectionBody)
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
                            setSelectedShowApprove(wishlist.status == "PENDING" || wishlist.status == "REJECTED");
                            setSelectedShowReject(wishlist.status == "PENDING" || wishlist.status == "APPROVED");
                        }}
                    >
                        View
                    </Button>
                </Center>
            </td>
            <td>{wishlist.feedback}</td>
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
            <Group spacing='xl' position={"right"}>
                {selectedShowApprove && <Button color={'green'} onClick={() => { approve(selectedStudentID, 'APPROVED') }}>Approve</Button>}
                {!selectedShowApprove &&  selectedShowReject && <Text color='green'>Already Approved</Text>}
                {selectedShowReject && <Button color={'red'} onClick={() => {setRejectionFeedbackOpened(true)}}>Reject</Button>}
            </Group>
        </Modal>
            <Modal opened={rejectionFeedbackOpened}
                size={"50%"}
                centered={false}
                onClose={() => setRejectionFeedbackOpened(false)}
                withCloseButton={false}
                closeOnClickOutside={false}
                closeOnEscape={false}
            >
                <Flex direction={"column"} gap={"xs"}>
                    <TextInput value={rejectionFeedback} onChange={(event) => setRejectionFeedback(event.currentTarget.value)} label={"Rejection Feedback"} placeholder={"Please a feedback for your rejection..."}></TextInput>
                    <Flex justify={"right"} gap={"xs"}>
                        <Button color={"red"} onClick={() => { setRejectionFeedbackOpened(false); setRejectionFeedback(""); }}>Cancel Rejection</Button>
                        <Button color={"green"} onClick={() => { setRejectionFeedbackOpened(false); reject(selectedStudentID, 'REJECTED', rejectionFeedback)}}>Confirm Rejection</Button>
                    </Flex>

                </Flex>

            </Modal>
            <Table striped withBorder withColumnBorders>
                <thead>
                    <tr>
                        <th>Student ID</th>
                        <th>View Wishlist</th>
                        <th>Rejection Feedback</th>
                    </tr>
                </thead>
                <tbody>
                    {waitingApprovalRows}
                </tbody>

            </Table></>);
}

export default ApproveWishlistsTable;