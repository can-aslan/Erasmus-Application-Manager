import { Box, Center, ScrollArea, Stack, Table, Title } from "@mantine/core";
import { useQuery } from "@tanstack/react-query";
import { getSubmittedPreApprovals } from "../../api/FACMember/PreapprovalService";
import ApprovePreApprovalsTable from "../../components/tables/ApprovePreApprovalsTable";
import { useUser } from "../../provider/UserProvider";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const ApprovePreApprovalsPage = () => {
    const { user } = useUser()
    const { data, isError, isLoading } = useQuery({
        queryFn: () => getSubmittedPreApprovals(user?.id!)
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
                sx={{ minWidth: 300 }}
                mx="auto"
            >
                <Stack
                    spacing='xl'
                >
                    <Title color='blue' size='36px'>Submitted PreApprovals</Title>
                    <ApprovePreApprovalsTable preApprovals={data} />
                </Stack>
            </Box>
        </Center>
    );
}
export default ApprovePreApprovalsPage;