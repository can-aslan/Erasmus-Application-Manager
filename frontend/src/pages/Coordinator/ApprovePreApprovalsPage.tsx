import { Box, Center, ScrollArea, Stack, Table, Title } from "@mantine/core";
import { useQuery } from "@tanstack/react-query";
import { getSubmittedPreApprovalsCoord } from "../../api/Coordinator/PreapprovalService";
import ApprovePreApprovalsTable from "../../components/tables/ApprovePreApprovalsTable";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useUser } from "../../provider/UserProvider";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const CoordinatorApprovePreApprovalsPage = () => {
    const { user } = useUser()
    const axiosSecure = useAxiosSecure()
    const { data, isError, isLoading } = useQuery({
        queryKey: ['getPreApprovalsCoord'],
        queryFn: () => getSubmittedPreApprovalsCoord(axiosSecure, user.id)
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
            
    console.log(data)
    return (
        <Center>
            <Box
                sx={{ minWidth: 300 }}
                mx="auto"
            >
                <Stack
                    spacing='xl'
                >
                    <Title color='blue' size='36px'>Submitted PreApprovals</Title>
                    <ApprovePreApprovalsTable preApprovals={data.data} />
                </Stack>
            </Box>
        </Center>
    );
}
export default CoordinatorApprovePreApprovalsPage;