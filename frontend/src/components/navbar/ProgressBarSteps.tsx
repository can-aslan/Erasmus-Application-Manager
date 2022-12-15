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
            active={active} 
            size='sm'
            sx={(theme) => ({
                '@media (max-width: 1400px)': {
                    width: 500
                },
                '@media (max-width: 1200px)': {
                    width: 400
                },
                '@media (max-width: 992px)': {
                    maxWidth: 100
                },
                '@media (max-width: 768px)': {
                    display: 'none'
                },
                '@media (max-width: 576px)': {
                    display: 'none'
                }
            })}
            
        >
                {pbSteps}
        </Stepper>
    );
}
 
export default ProgressBarSteps;