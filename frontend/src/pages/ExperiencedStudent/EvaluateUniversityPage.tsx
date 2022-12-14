import { Button, Flex, Textarea, TextInput, Title } from "@mantine/core";
import { IconSchool } from "@tabler/icons";
import { SetStateAction, useState } from "react";
import Evaluation from "../../components/evaluation/Evaluation";
import RatingBar from "../../components/rating/RatingBar";


const EvaluateUniversityPage = () => {
    const [givenRating, setGivenRating] = useState(0);
    const [uniName, setUniName] = useState("Vrije Universiteit");
    const description = `You can evaluate ${uniName} in terms of food, dormitories, social opportunities, people, campus facilities etc.`;
    const [currentEvaluation, setCurrentEvaluation] = useState("");
    return (
        <Evaluation editable={true} emptySymbol={<IconSchool/>} fullSymbol={<IconSchool color={"#1971c2"}/>} givenRating={givenRating} evaluationName={uniName} currentEvaluation={currentEvaluation} setGivenRating={setGivenRating} setCurrentEvaluation={setCurrentEvaluation} description={description}></Evaluation>

    );
}

export default EvaluateUniversityPage;