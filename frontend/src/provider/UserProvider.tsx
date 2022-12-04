import { createContext, useContext, useState } from "react";
import { User } from "../types";

interface UserContextProps {
    user: User | {},
    isUser: (x: any) => x is User,
    setUser: React.Dispatch<React.SetStateAction<User | {}>>
}

interface UserProviderProps {
    children: React.ReactNode
}

const UserContext = createContext<UserContextProps>(null!)

export const UserProvider = ({ children }: UserProviderProps) => {
    const [user, setUser] = useState<User | {}>({})
    const isUser = (x: any): x is User => Object.keys(x).length !== 0

    return (
        <UserContext.Provider value={{user, setUser, isUser}}>
            {children}
        </UserContext.Provider>
    )
}

export const useUser = () => {
    return useContext(UserContext)
}