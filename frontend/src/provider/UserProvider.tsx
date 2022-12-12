import { User } from "../types";

export const useUser = (): {user: User, setUser: (user: User | null) => void } => {
    const user = getUser()

    return { user, setUser }
}

function getUser() {
    const user = localStorage.getItem("user")
    if (user) {
        return JSON.parse(user)
    }

    return null
}

function setUser(user: User | null) {
    localStorage.setItem("user", JSON.stringify({...user}))
}