import { Card, Divider, Flex, Rating, Text } from "@mantine/core";
import { IconSchool } from "@tabler/icons";
import { PastEvaluationItem } from "../../types";

interface EvaluationCardProps {
    evaluation: PastEvaluationItem,
    emptyRatingIcon: React.ReactNode,
    fullRatingIcon: React.ReactNode,
}

const EvaluationCard = ({evaluation, emptyRatingIcon, fullRatingIcon}: EvaluationCardProps) => {
    return (
        <Card 
            shadow="xl"
            radius="xl"
            p={24}
        >
            <Flex
                gap="md"
                direction='column'
            >
                <Rating
                    emptySymbol={emptyRatingIcon} 
                    fullSymbol={fullRatingIcon}
                    value={evaluation.rate}
                    readOnly
                />
                <Divider/>
                <Text
                    sx={{
                        fontSize: 16
                    }}
                >
                    {evaluation.comment}
                </Text>
            </Flex>
        </Card>
    );
}
 
export default EvaluationCard;