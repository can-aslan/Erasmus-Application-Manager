import { Flex, Title, Textarea, Button, Text } from "@mantine/core";
import { IconSchool } from "@tabler/icons";
import { useState } from "react";
import RatingBar from "../rating/RatingBar";

interface EvaluationProps {
    givenRating: number;
    evaluationName: string
    description: string;
    currentEvaluation: string;
    setGivenRating: React.Dispatch<React.SetStateAction<number>>;
    setCurrentEvaluation: React.Dispatch<React.SetStateAction<string>>;
    emptySymbol: React.ReactNode;
    fullSymbol: React.ReactNode;
    editable: boolean

}
const Evaluation = ({ editable, emptySymbol, fullSymbol, evaluationName, description, givenRating, currentEvaluation, setGivenRating, setCurrentEvaluation }: EvaluationProps) => {
    return (
        <>
            <Flex direction={"column"} gap={"xl"}>
                <Title color='blue' size='36px'>Evaluate {evaluationName}</Title>
                <Flex direction={"row"} gap={"xs"}>
                    <Text>Rate: </Text>
                    <RatingBar emptySymbol={emptySymbol} fullSymbol={fullSymbol} value={givenRating} setValue={setGivenRating}></RatingBar>
                </Flex>
                <Textarea value={currentEvaluation} onChange={(event) => setCurrentEvaluation(event.currentTarget.value)} autosize={true} minRows={10}
                    sx={{ maxWidth: "50%" }} placeholder={description}
                    description={description}>
                </Textarea>
                <Flex direction={"row"} justify={"left"} gap={"xs"}>
                    <Button disabled={!editable || (currentEvaluation == "")} onClick={() => { }}>Save</Button>
                    <Button disabled={!editable || (currentEvaluation == "")} onClick={() => { }}>Submit</Button>
                </Flex>
            </Flex>
        </>
    );
}

export default Evaluation;