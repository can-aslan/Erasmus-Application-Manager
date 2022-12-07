import { Button } from "@mantine/core";
import { useNavigate } from "react-router-dom";

const Unauthorized = () => {
    const navigate = useNavigate()

    const goBack = () => navigate(-1)

    return (
        <section>
            <h1>Unauthorized</h1>
            <p>You do not have access to this page.</p>
            <div>
                <Button onClick={goBack}>Go back</Button>
            </div>
        </section>
    );
}
 
export default Unauthorized;