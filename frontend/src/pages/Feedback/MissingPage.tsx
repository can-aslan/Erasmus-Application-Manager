import { Button } from "@mantine/core";
import { useNavigate } from "react-router-dom";

const MissingPage = () => {
    const navigate = useNavigate()

    const goBack = () => navigate(-1)

    return (
        <section>
            <h1>Page not found!</h1>
            <p>The requested URL does not exist.</p>
            <div>
                <Button onClick={goBack}>Go back</Button>
            </div>
        </section>
    );
}
 
export default MissingPage;