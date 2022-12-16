import { Center, Flex, Group, Header, Title } from "@mantine/core";
import UniversitiesTable from "../../components/tables/UniversitiesTable";
import ToggleThemeIcon from "../../components/ToggleThemeIcon";
import { ResponseUniversities } from "../../types/responseTypes";

const GuestPage = () => {
    const universities: ResponseUniversities = {
        data: [
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

        ],
        message: ""
    }
    return (
        <>
            <Header
                height={80}
                p="lg"
            >
                <Group position="apart">
                    <Title ml={50}>Beam</Title>
                    <ToggleThemeIcon mr={50} />
                </Group>
            </Header>
            <Center>
                <Flex direction={"column"} gap={"md"}>
                    <Title>Welcome! Below you can find the universities that you can apply!</Title>
                <UniversitiesTable universities={universities.data} isUniversitiesLoading={false} ></UniversitiesTable>
                </Flex>
                
            </Center>
        </>
    );
}

export default GuestPage;