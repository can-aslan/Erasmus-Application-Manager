import { Button, Flex, Textarea, TextInput, Title } from "@mantine/core";
import { IconSchool } from "@tabler/icons";
import { useState } from "react";
import RatingBar from "../../components/rating/RatingBar";


const EvaluateUniversityPage = () => {
    const [givenRating, setGivenRating] = useState(0);
    const [uniName, setUniName] = useState("Vrije Universiteit");
    const [currentEvaluation, setCurrentEvaluation] = useState("");
    return (
        <>
            <Flex direction={"column"} gap={"xl"}>
                <Title color='blue' size='36px'>Evaluate {uniName}</Title>
                <RatingBar emptySymbol={<IconSchool />} fullSymbol={<IconSchool color={"#1971c2"} />} value={givenRating} setValue={setGivenRating}></RatingBar>
                <Textarea value={currentEvaluation} onChange={(event) => setCurrentEvaluation(event.currentTarget.value)} autosize={true} minRows={10}
                    sx={{ maxWidth: "50%" }} placeholder={`You can evaluate ${uniName} in terms of food, dormitories, social opportunities, people, campus facilities etc.`}
                    description={`You can evaluate ${uniName} in terms of food, dormitories, social opportunities, people, campus facilities etc.`}>
                </Textarea>
                <Flex direction={"row"} justify={"left"} gap={"xs"}>
                    <Button disabled={currentEvaluation == ""} onClick={() => { }}>Save</Button>
                    <Button disabled={currentEvaluation == ""} onClick={() => { }}>Submit</Button>
                </Flex>
            </Flex>
        </>

    );
}

export default EvaluateUniversityPage;