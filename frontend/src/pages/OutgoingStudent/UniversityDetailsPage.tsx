import { Accordion, Anchor, Badge, Card, Center, Flex, Header, Image, List, Text, TextInput, Title } from "@mantine/core";
import { AccordionControl } from "@mantine/core/lib/Accordion/AccordionControl/AccordionControl";
import { useParams } from "react-router-dom";
import { UniversityDetailed, UniversityProxy } from "../../types";

const UniversityDetails = () => {
    const { universityId } = useParams()
    const universityProxy = JSON.parse(localStorage.getItem('universityProxy') || '') as unknown as UniversityProxy

    // Fetch University details
    // const {data: university, isError: isUniError, isLoading: isUniLoading} = useUniversity(universityId!)
    // if (isUniLoading) {
    //     return <LoadingPage />
    // }
    // if (isUniError) {
    //     return <ErrorPage message="We could not find the university you are looking for."/>
    // }
    const { 
        universityName,
        country,
        city,
        studentGrant,
        dormitory,
        specialCase,
    } = universityProxy

    const university: UniversityDetailed = {
        acceptedDepartmentsInBilkent: ["CS", "ME", "EEE"],
        generalInfo: "As a public research university, our mission is the creation, dissemination, preservation and application of knowledge for the betterment of our global society. UCLA combines the close-knit learning environment of a spirited public school with the endless opportunities of a world-class city. Located in beautiful Westwood, minutes from Hollywood and the downtown city center of Los Angeles, the university offers everything you need to reach your full potential.",
        universityWebsite: "www.website.com",
        bgImage: '',
        logoImage: '',
    }

    return (
        <Flex direction='column' gap={36}>
            <Image 
                width='100%'
                height='300px'
                src='https://w3.bilkent.edu.tr/www/wp-content/uploads/sites/5/2019/07/Leiden-Report-b%C3%BCy%C3%BCk-1024x662.jpg'
                radius='sm'
                fit="cover"
            />
            <Card shadow="lg" radius="lg">
                <Accordion sx={{fontSize: 20}}>
                    <Accordion.Item value="quick-info">
                        <Accordion.Control>Quick Information</Accordion.Control>
                        <Accordion.Panel>
                            <List>
                                <List.Item>
                                    <Text fw={600} span>University Name: </Text><Text span>{universityName}</Text>
                                </List.Item>
                                <List.Item>
                                    <Text fw={600} span>University Webpage: </Text><Anchor href={university.universityWebsite} span>{university.universityWebsite}</Anchor>
                                </List.Item>
                                <List.Item>
                                    <Text fw={600} span>Country: </Text><Text span>{country}</Text>
                                </List.Item>
                                <List.Item>
                                    <Text fw={600} span>City: </Text><Text span>{city}</Text>
                                </List.Item>
                                <List.Item>
                                    <Text fw={600} span>Student grant: </Text><Text span>€{studentGrant}</Text>
                                </List.Item>
                                <List.Item>
                                    <Text fw={600} span>Dormitory: </Text>
                                    {dormitory ? 
                                            <Text color='green' span>Offers dormitory</Text> 
                                            : <Text color='red' span>Does not offer dormitory </Text>}
                                </List.Item>
                            </List>
                        </Accordion.Panel>
                    </Accordion.Item>
                    <Accordion.Item value="general-info">
                        <Accordion.Control>General Information</Accordion.Control>
                        <Accordion.Panel p={18}> 
                            {university.generalInfo}
                        </Accordion.Panel>
                    </Accordion.Item>
                    <Accordion.Item value="acceptance-conditions & Special Cases">
                        <Accordion.Control>Acceptance Conditions</Accordion.Control>
                        <Accordion.Panel>
                            <Flex direction='column' gap={24}>
                                <section>
                                    <Title mb={12} order={3}>Accepted departments</Title>
                                    <Flex gap={12}>
                                        {university.acceptedDepartmentsInBilkent.map(d => <Badge p={12} size="xl">{d}</Badge>)}
                                    </Flex>
                                </section>
                                <section>
                                    <Title mb={12} order={3}>Accepted semesters</Title>
                                    <Flex>
                                        {universityProxy?.specialCase?.semesterLimit?.map((s) => <Badge color='teal' size="xl">{s}</Badge>)}
                                    </Flex>
                                </section>
                                <section>
                                    <Title mb={12} order={3}>Language Requirements</Title>
                                    {universityProxy?.specialCase?.languageRequirements?.map((s) => <Badge color='red' size="xl">{s}</Badge>)}
                                </section>
                            </Flex>

                        </Accordion.Panel>
                    </Accordion.Item>
                    <Accordion.Item value="contact">
                        <Accordion.Control>Previously Visited Students</Accordion.Control>
                        <Accordion.Panel p={18}> 
                        </Accordion.Panel>
                    </Accordion.Item>
                </Accordion>
            </Card>
        </Flex>
    );
}
 
export default UniversityDetails;