import { Card, TextInput } from "@mantine/core";
import { useParams } from "react-router-dom";
import { useUniversity } from "../../hooks/useUniversity";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const UniversityDetails = () => {
    const { universityId } = useParams()
    // const {data: university, isError: isUniError, isLoading: isUniLoading} = useUniversity(universityId!)

    // if (isUniLoading) {
    //     return <LoadingPage />
    // }
    // if (isUniError) {
    //     return <ErrorPage message="We could not find the university you are looking for."/>
    // }

    return (
        <Card shadow="lg" radius="lg">
            <TextInput></TextInput>
        </Card>
    );
}
 
export default UniversityDetails;