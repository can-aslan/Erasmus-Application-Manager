import { Stepper } from "@mantine/core";

const ProgressBar = () => {
    return (
        <Stepper active={3} breakpoint="sm">
            <Stepper.Step label="First step" description="Create an account">
                Step 1 content: Create an account
            </Stepper.Step>
            <Stepper.Step label="Second step" description="Verify email">
                Step 2 content: Verify email
            </Stepper.Step>
            <Stepper.Step label="Final step" description="Get full access">
                Step 3 content: Get full access
            </Stepper.Step>
        </Stepper>
    );
}

export default ProgressBar;