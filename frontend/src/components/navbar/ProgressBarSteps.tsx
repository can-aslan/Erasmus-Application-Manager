import { Stepper } from "@mantine/core";
import { ProgressBarStep } from "../../types";

interface ProgressBarStepsProps{
    steps: Array<ProgressBarStep>
    active: number
}
const ProgressBarSteps = ({active,steps}: ProgressBarStepsProps) => {
    const pbSteps = steps.map((step) => {
        return (
            <Stepper.Step label={step.label} description={step.description} />
        )
    });

    return ( 
        <Stepper 
         active={active} breakpoint="sm">
            {pbSteps}
        </Stepper>
    );
}
 
export default ProgressBarSteps;