import { Loader } from '@mantine/core';
import { useEffect, useState } from 'react';
import { Outlet } from 'react-router-dom';
import useRefreshToken from '../../hooks/useRefreshToken';
import { useUser } from '../../provider/UserProvider';

const PersistentLogin = () => {
    const [isLoading, setIsLoading] = useState(true)
    const refresh = useRefreshToken()
    const { user, isUser } = useUser()

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

        if (isUser(user)) {
            !user?.accessToken ? verifyRefreshToken() : setIsLoading(false)
        }

        return () => {
            isMounted = false
        }
    }, [])
    
    return (
        isLoading ? <Loader /> : <Outlet />
    );
}
 
export default PersistentLogin;