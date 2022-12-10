import { IconSchool } from "@tabler/icons";
import { useState } from "react";
import RatingBar from "../../components/rating/RatingBar";


const EvaluateUniversityPage = () => {
    const [givenRating, setGivenRating] = useState(0);
    return (
        <RatingBar emptySymbol={<IconSchool />} fullSymbol={<IconSchool color={"orange"} />} value={givenRating} setValue={setGivenRating}></RatingBar>
    );
}

export default EvaluateUniversityPage;