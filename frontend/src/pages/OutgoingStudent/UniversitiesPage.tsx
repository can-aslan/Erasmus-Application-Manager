import { Box } from "@mantine/core";
import UniversitiesTable from "../../components/tables/UniversitiesTable";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { UniversityProxy } from "../../types";
import { ResponseUniversities } from "../../types/responseTypes";


const UniversitiesPage = () => {
    const axiosSecure = useAxiosSecure()
    // Fetch universities.
    // const {
    //     data: universities,
    //     isError: isUniversitiesError,
    //     isLoading: isUniversitiesLoading 
    // } = useUniversities(axiosSecure)

    const universities: UniversityProxy[] = [
                {
                    city: "Amsterdam",
                    country: "Netherlands",
                    id: "1",
                    universityName: "Vrije",
                    dormitory: true,
                    specialCase: {
                        languageRequirements: ['German B2'],
                        semesterLimit: ["Fall"]
                    },
                    studentGrant: 500,
                },
                {
                    city: "London",
                    country: "UK",
                    id: "2",
                    universityName: "King's College",
                    dormitory: true,
                    specialCase: {
                        languageRequirements: [],
                        semesterLimit: []
                    },
                    studentGrant: 600,
                },
                {
                    city: "Amsterdam",
                    country: "Netherlands",
                    id: "3",
                    universityName: "Vrije",
                    dormitory: true,
                    specialCase: {
                        languageRequirements: [],
                        semesterLimit: []
                    },
                    studentGrant: 500,
                },
                {
                    city: "London",
                    country: "UK",
                    id: "4",
                    universityName: "King's College",
                    dormitory: true,
                    specialCase: {
                        languageRequirements: [],
                        semesterLimit: []
                    },
                    studentGrant: 600,
                },
                {
                    city: "Amsterdam",
                    country: "Netherlands",
                    id: "5",
                    universityName: "Vrije",
                    dormitory: true,
                    specialCase: {
                        languageRequirements: [],
                        semesterLimit: []
                    },
                    studentGrant: 500,
                },
                {
                    city: "London",
                    country: "UK",
                    id: "6",
                    universityName: "King's College",
                    dormitory: true,
                    specialCase: {
                        languageRequirements: [],
                        semesterLimit: []
                    },
                    studentGrant: 600,
                },
            ]        

    const isUniversitiesError = false
    const isUniversitiesLoading = false

    return (
        <Box>
            <UniversitiesTable 
                universities={universities || []}
                isUniversitiesLoading={isUniversitiesLoading}
            />
        </Box>
    );
}
 
export default UniversitiesPage;