import { Center, Box, Stack} from "@mantine/core";
import { useQuery } from "@tanstack/react-query";
import { getWaitingRequestedCourses } from "../../api/Instructor/CourseRequestService";
import ApproveRequestedCoursesGrid from "../../components/grids/ApproveRequestedCoursesGrid";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { useUser } from "../../provider/UserProvider";
import { CourseRequest } from "../../types";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";


const ApproveCourseRequestPage = () => {
    const axiosSecure = useAxiosSecure();
    const { user } = useUser();
    
    const { data: dataRequests, isError: isRequestsError, isLoading: isRequestsLoading } = useQuery({
        queryFn: () => getWaitingRequestedCourses(axiosSecure, user.id),
        queryKey: ["getWaitingRequests"],
    })

    if (isRequestsLoading)
    {
        return <LoadingPage/>
    }

    if (isRequestsError)
    {
        return <ErrorPage/>
    }
    const waitingRequests = dataRequests.data;

    return (<Center sx={{ height: '100vh' }}>
        <Box
            sx={{ minWidth: 300 }}
            mx="auto"
        >
            <Stack
                spacing='xl'
            >
                <ApproveRequestedCoursesGrid waitingCourses={waitingRequests} />
            </Stack>
        </Box>
    </Center>);
}

export default ApproveCourseRequestPage;