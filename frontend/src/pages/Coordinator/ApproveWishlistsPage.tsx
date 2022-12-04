import { Box, Center, ScrollArea, Stack, Table, Title } from "@mantine/core";
import ApproveWishlistsTable from "../../components/tables/ApproveWishlistsTable";

const ApproveWishlistsPage = () => {
    return(
    <Center>
        <Box 
                sx={{minWidth: 300}} 
                mx="auto"
            >
                <Stack 
                    spacing='xl'
                >
                    <Title color='blue' size='36px'>Submitted Wishlists</Title>
                    <ApproveWishlistsTable />
                </Stack>
            </Box>
    </Center>
    );
}
export default ApproveWishlistsPage;