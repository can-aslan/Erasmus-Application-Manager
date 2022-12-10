import { IconSchool } from "@tabler/icons";
import RatingBar from "../../components/rating/RatingBar";

const EvaluateUniversityPage = () => {
    return (  
        <RatingBar emptySymbol={<IconSchool/>} fullSymbol={<IconSchool color={"red"}/>}></RatingBar>
    );
}
 
export default EvaluateUniversityPage;