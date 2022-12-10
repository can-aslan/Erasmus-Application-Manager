import { Rating } from "@mantine/core";
import { IconSchool, TablerIcon } from "@tabler/icons";

interface RatingBarProps{
    emptySymbol: React.ReactNode;
    fullSymbol: React.ReactNode

}
const RatingBar = ({emptySymbol, fullSymbol}:RatingBarProps) => {
    return ( 
        <Rating fractions={2} emptySymbol={emptySymbol} fullSymbol={fullSymbol}/>
     );
}
 
export default RatingBar;