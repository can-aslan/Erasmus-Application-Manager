import { useMutation } from "@tanstack/react-query"
import { approveWishlist, rejectWishlist } from "../api/Coordinator/CourseWishlistService"
import { useUser } from "../provider/UserProvider"

export const useRejectWishlist = () => {
    const { user } = useUser()
    return useMutation({
        mutationKey: ['rejectWishlist'],
        mutationFn: (wishlistId: string) => rejectWishlist(user!.uuid, wishlistId),
    })
}