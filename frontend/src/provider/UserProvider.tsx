import { createContext, useContext, useReducer, useState } from "react";
import { User } from "../types";

interface UserContextProps {
    user: User | null,
}

interface UserProviderProps {
    children: React.ReactNode
}

const UserContext = createContext<UserContextProps | null>(null)
const UserDispatchContext = createContext<React.Dispatch<ACTION_TYPE>>(null!)

type ACTION_TYPE = {type: "LOGIN", payload: User} | {type: "LOGOUT", payload: User | null}

export const UserProvider = ({ children }: UserProviderProps) => {
    const [user, dispatch] = useReducer(reducer, null)

    return (
        <UserContext.Provider value={{user}}>
            <UserDispatchContext.Provider value={dispatch}>
                {children}
            </UserDispatchContext.Provider>
        </UserContext.Provider>
    )
}

export const useUser = () => {
    return useContext(UserContext)
}

export const useUserDispatch = () => {
    return useContext(UserDispatchContext)
}

function reducer(state: User | null, action: ACTION_TYPE) {
    switch(action.type) {
        case "LOGIN":
            state = action.payload
            console.log('Logged in')
            return state
        case "LOGOUT":
            return null
    }
}