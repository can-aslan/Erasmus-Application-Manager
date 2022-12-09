import { Button, Center, Stack } from "@mantine/core";
import { useNavigate } from "react-router-dom";

const MissingPage = () => {
    const navigate = useNavigate()

    const goBack = () => navigate(-1)

    return (
        <Center sx={{height: '87vh'}}>
            <Stack align='center'>
                <section>
                    <h1>Page not found!</h1>
                    <p>The requested URL does not exist.</p>
                    <div>
                        <Button onClick={goBack}>Go back</Button>
                    </div>
                </section>
            </Stack>
        </Center>
    );
}
 
export default MissingPage;