import { Box } from "@mantine/core";
import UniversitiesTable from "../../components/tables/UniversitiesTable";
import { useUniversities } from "../../hooks/useUniversities";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

const UniversitiesPage = () => {
    // TODO: create and call useUniversities() to fetch universities.
    const {
        data: universities,
        isError: isUniversitiesError,
        isLoading: isUniversitiesLoading 
    } = useUniversities()

    if (isUniversitiesLoading) {
        return <LoadingPage />
    }

    if (isUniversitiesError) {
        return <ErrorPage />
    }

    return (
        <Box sx={{height: '50vh'}}>
            <UniversitiesTable records={universities.data || []}/>
        </Box>
    );
}
 
export default UniversitiesPage;