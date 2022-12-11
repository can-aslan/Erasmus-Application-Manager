import { Box, Center, Stack, Title } from "@mantine/core";
import { useQuery } from "@tanstack/react-query";
import { getAllStudentWishlists } from "../../api/Coordinator/CourseWishlistService";
import ApproveWishlistsTable from "../../components/tables/ApproveWishlistsTable";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useUser } from "../../provider/UserProvider";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const ApproveWishlistsPage = () => {
    const axiosSecure = useAxiosSecure()
    const { user } = useUser()
    const { data, isError, isLoading } = useQuery({
        queryFn: () => getAllStudentWishlists(axiosSecure, user?.id!)
    })

    if (isLoading) {
        return (
            <LoadingPage />
        )
    }

    if (isError || !data) {
        return (
            <ErrorPage />
        )
    }

    return (
        <Center>
            <Box 
                sx={{minWidth: 300}} 
                mx="auto"
            >
                <Stack 
                    spacing='xl'
                >
                    <Title color='blue' size='36px'>Submitted Wishlists</Title>
                    <ApproveWishlistsTable wishlists={data.data}/>
                </Stack>
            </Box>
        </Center>
    );
}
export default ApproveWishlistsPage;