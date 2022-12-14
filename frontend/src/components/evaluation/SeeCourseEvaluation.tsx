import { Card, Flex, Rating, Text, Textarea } from "@mantine/core";
import { IconBook } from "@tabler/icons";
import { PastEvaluationItem } from "../../types";

interface SeeCourseEvaluationProps {
    evalList: Array<PastEvaluationItem>;
}
const SeeCourseEvaluation = ({ evalList }: SeeCourseEvaluationProps) => {
    return (
        <>
            {
                evalList.map((evaluation) => {
                    return (
                        <Card maw={500} shadow={"xl"} radius={"md"}>
                            <Flex direction={"column"} gap={"sm"}>
                                <Flex direction={"row"} gap={"sm"}>
                                    <Text maw={500}> Rate: </Text> <Rating emptySymbol={<IconBook></IconBook>} fullSymbol={<IconBook color="#1971c2" />} readOnly={true} fractions={2} value={evaluation.rate}></Rating>
                                </Flex>
                                <Textarea label={"Evaluation: "} autosize disabled={true} value={evaluation.comment}> { }</Textarea>
                            </Flex>
                        </Card>
                    );
                })
            }   
        </>

    );
}

export default SeeCourseEvaluation;