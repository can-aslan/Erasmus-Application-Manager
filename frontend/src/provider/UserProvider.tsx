import { createContext, useContext, useState } from "react";
import { User } from "../types";

interface UserContextProps {
    user: User | {},
    setUser: React.Dispatch<React.SetStateAction<User | {}>>
}

interface UserProviderProps {
    children: React.ReactNode
}

const UserContext = createContext<UserContextProps>(null!)

export const UserProvider = ({ children }: UserProviderProps) => {
    const [user, setUser] = useState<User | {}>({})

    return (
        <UserContext.Provider value={{user, setUser}}>
            {children}
        </UserContext.Provider>
    )
}

export const useUser = () => {
    return useContext(UserContext)
}