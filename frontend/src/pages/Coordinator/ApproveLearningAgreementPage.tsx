import { Center, Box, Stack, Title } from "@mantine/core"
import { useQuery } from "@tanstack/react-query"
import ApproveLearningAgreementsTable from "../../components/tables/ApproveLearningAgreementsTable"
import ApprovePreApprovalsTable from "../../components/tables/ApprovePreApprovalsTable"
import useAxiosSecure from "../../hooks/useAxiosSecure"
import { useUser } from "../../provider/UserProvider"
import { LearningAgreement } from "../../types"
import ErrorPage from "../Feedback/ErrorPage"
import LoadingPage from "../Feedback/LoadingPage"

const ApproveLearningAgreementPage = () => {
    // const { user } = useUser()
    // const axiosSecure = useAxiosSecure()
    // const { data, isError, isLoading } = useQuery({
    //     queryFn: () => getSubmittedLearningAgreements(axiosSecure, user?.id!)
    // })

    // if (isLoading) {
    //     return (
    //         <LoadingPage />
    //     )
    // }

    // if (isError || !data) {
    //     return (
    //         <ErrorPage />
    //     )
    // }

    //TODO: üstü uncommentle, 'la' yerine data.data ver aşağıda
    const la: Array<LearningAgreement> = [
        {
            formUuid: "1",
            studentName: "Can Ersoy",
            studentId: "22003216",
            status: "Rejected",
            rejectionFeedback: "Always happens, nothing unusual",
        },
        {
            formUuid: "2",
            studentName: "Selim Can Güler",
            studentId: "22002811",
            status: "Pending",
            rejectionFeedback: "",
        },
        {
            formUuid: "3",
            studentName: "İlkim Elif Kervan",
            studentId: "22002223",
            status: "Approved",
            rejectionFeedback: "",
        },
        {
            formUuid: "4",
            studentName: "Yağız Can Aslan",
            studentId: "22001943",
            status: "Approved",
            rejectionFeedback: "",
        },
        {
            formUuid: "5",
            studentName: "Kemal Kubilay Yılmaz",
            studentId: "21901556",
            status: "Approved",
            rejectionFeedback: "",
        }
    ];
    return (
        <Center>
            <Box
                sx={{ minWidth: 300 }}
                mx="auto"
            >
                <Stack
                    spacing='xl'
                >
                    <Title color='blue' size='36px'>Submitted Learning Agreements</Title>
                    <ApproveLearningAgreementsTable learningAgreements={la} />
                </Stack>
            </Box>
        </Center>
    );
}

export default ApproveLearningAgreementPage;