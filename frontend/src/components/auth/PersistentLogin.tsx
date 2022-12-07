import { Center, Loader, Stack, Text } from '@mantine/core';
import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import useRefreshToken from '../../hooks/useRefreshToken';
import { useUser } from '../../provider/UserProvider';

const PersistentLogin = () => {
    const [isLoading, setIsLoading] = useState(true)
    const refresh = useRefreshToken()
    const { user } = useUser()

    useEffect(() => {
        let isMounted = true
        
        const verifyRefreshToken = async () => {
            try {
                await refresh()
            }
            catch (error) {
                console.error(error)
            }
            finally {
                setIsLoading(false)
            }
        }

        !user?.accessToken ? verifyRefreshToken() : setIsLoading(false)
        
        return () => {
            isMounted = false
        }
    }, [])
    
    return (
        isLoading ? (
            <Center sx={{height: '100vh'}}>
                <Stack align='center'>
                    <Loader  size={60}/>
                    <Text size={22} color='blue'>Please wait while we get things ready for you!</Text>
                </Stack>
            </Center>
        ) : <Outlet />
    );
}
 
export default PersistentLogin;