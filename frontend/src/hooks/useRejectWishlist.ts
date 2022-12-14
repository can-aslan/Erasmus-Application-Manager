import { useMutation } from "@tanstack/react-query"
import { AxiosInstance } from "axios"
import { approveWishlist, rejectWishlist } from "../api/Coordinator/CourseWishlistService"
import { useUser } from "../provider/UserProvider"

export const useRejectWishlist = (axiosSecure: AxiosInstance) => {
    const { user } = useUser()
    return useMutation({
        mutationKey: ['rejectWishlist'],
        mutationFn: (wishlistId: string) => rejectWishlist(axiosSecure, user!.id, wishlistId),
    })
}