import { Center, Stack, Text } from '@mantine/core';
import { useQuery } from '@tanstack/react-query';
import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import useRefreshToken from '../../hooks/useRefreshToken';
import { useUser } from '../../provider/UserProvider';
import ScreenLoadingLottie from '../Loader/ScreenLoadingLottie';

const PersistentLogin = () => {
    const [isLoading, setIsLoading] = useState(true)
    const refresh = useRefreshToken()
    const { user } = useUser()

    useEffect(() => {
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

    }, [])

    return (
        isLoading ? (
            <Center sx={{ height: '100vh' }}>
                <Stack align='center'>
                    <ScreenLoadingLottie/>
                    <Text size={22} color='blue'>Please wait while we get things ready for you!</Text>
                </Stack>
            </Center>
        ) : <Outlet />
    );
}

export default PersistentLogin;