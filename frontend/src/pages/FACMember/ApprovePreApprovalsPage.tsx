import { Box, Center, ScrollArea, Stack, Table, Title } from "@mantine/core";
import ApprovePreApprovalsTable from "../../components/tables/ApprovePreApprovalsTable";

const ApprovePreApprovalsPage = () => {
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
                    <ApprovePreApprovalsTable />
                </Stack>
            </Box>
        </Center>
    );
}
export default ApprovePreApprovalsPage;