import { Stepper } from "@mantine/core";
import { ProgressBarStep, User } from "../../types";
import ProgressBarSteps from "./ProgressBarSteps";
interface GenericProgressBarProps {
    user: User,
    progressBarSteps: Array<ProgressBarStep>
    active:number
}
const GenericProgressBar = ({user, progressBarSteps, active}: GenericProgressBarProps) => {
    return (
        <ProgressBarSteps active={active} steps={progressBarSteps}></ProgressBarSteps>
    );
}

export default GenericProgressBar;