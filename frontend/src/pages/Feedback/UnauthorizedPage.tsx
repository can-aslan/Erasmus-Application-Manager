import { Button, Center, Stack } from "@mantine/core";
import { useNavigate } from "react-router-dom";

const Unauthorized = () => {
    const navigate = useNavigate()

    const goBack = () => navigate(-1)

    return (
        <Center sx={{height: '87vh'}}>
            <Stack align='center'>
            <section>
                <h1>Unauthorized</h1>
                <p>You do not have access to this page.</p>
                <div>
                    <Button onClick={goBack}>Go back</Button>
                </div>
            </section>
            </Stack>
        </Center>
    );
}
 
export default Unauthorized;