import { useMutation } from "@tanstack/react-query"
import { saveWishlist } from "../api/Student/CourseService"
import { useUser } from "../provider/UserProvider"
import { WishlistItemType } from "../types"

export const useSaveWishlist = (wishlistItems: Array<WishlistItemType>) => {
    const { user } = useUser()
    return useMutation({
        mutationKey: ['saveWishlist'],
        mutationFn: () => saveWishlist(user!.uuid, wishlistItems)
    })
}