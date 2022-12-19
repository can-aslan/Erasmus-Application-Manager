import { Center, Flex, Group, Header, Title } from "@mantine/core";
import { NavLink } from "react-router-dom";
import ToggleThemeIcon from "../../components/ToggleThemeIcon";
import GuestHeader from "../../components/navbar/GuestHeader";
import UniversitiesTable from "../../components/tables/UniversitiesTable";
import { ResponseUniversities } from "../../types/responseTypes";

const defaultStyle = {
    textDecoration: 'none',
}

const activeLinkStyle = {
    textDecoration: 'underline',
    color: '#1aac83',
}


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
            <GuestHeader/>
            <Center>
                <Flex direction={"column"} gap={"md"}>
                    <Title>Welcome! Below you can find the universities that you can apply!</Title>
                    <UniversitiesTable universities={universities.data} isUniversitiesLoading={false} />
                </Flex>
                
            </Center>
        </>
    );
}

export default GuestPage;