import { MantineColor, Rating } from "@mantine/core";

interface RatingBarProps{
    emptySymbol: React.ReactNode;
    fullSymbol: React.ReactNode;
    value: number
    setValue: (value: number) => void
}
const RatingBar = ({emptySymbol, fullSymbol, value, setValue}:RatingBarProps) => {
    return ( 
        <Rating onChange={setValue} value={value} fractions={2} emptySymbol={emptySymbol} fullSymbol={fullSymbol}/>
     );
}
 
export default RatingBar;