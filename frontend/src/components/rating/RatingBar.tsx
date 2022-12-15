import { MantineColor, Rating } from "@mantine/core";

interface RatingBarProps{
    editable: boolean;
    emptySymbol: React.ReactNode;
    fullSymbol: React.ReactNode;
    value: number
    setValue: (value: number) => void
}
const RatingBar = ({editable, emptySymbol, fullSymbol, value, setValue}:RatingBarProps) => {
    return ( 
        <Rating readOnly={!editable} onChange={setValue} value={value} fractions={2} emptySymbol={emptySymbol} fullSymbol={fullSymbol}/>
     );
}
 
export default RatingBar;