import { useMutation } from "@tanstack/react-query"
import { approveWishlist } from "../api/Coordinator/CourseWishlistService"
import { useUser } from "../provider/UserProvider"

export const useApproveWishlist = () => {
    const { user } = useUser()
    return useMutation({
        mutationKey: ['approveWishlist'],
        mutationFn: (wishlistId: string) => approveWishlist(user!.uuid, wishlistId),
    })
}