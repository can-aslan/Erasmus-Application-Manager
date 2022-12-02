import { createContext, useContext, useState } from "react";
import { User } from "../types";

interface UserContextProps {
    user: User | null,
    setUser: React.Dispatch<React.SetStateAction<User | null>>
}

interface UserProviderProps {
    children: React.ReactNode
}

const UserContext = createContext<UserContextProps | null>(null)

export const UserProvider = ({ children }: UserProviderProps) => {
    const [user, setUser] = useState<User | null>(null)

    return (
        <UserContext.Provider value={{user, setUser}}>
            {children}
        </UserContext.Provider>
    )
}

export const useUser = () => {
    return useContext(UserContext)
}