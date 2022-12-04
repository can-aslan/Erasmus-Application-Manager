import { Autocomplete, Button, Modal, Stack } from "@mantine/core";
import { IconPlus } from '@tabler/icons';
import { useState } from 'react';
import { WishlistItemType } from "../../types";
import WishlistItem from "./WishlistItem";

interface WishlistProps {
    wishlistItems: Array<WishlistItemType>
}

const Wishlist = ({ wishlistItems }: WishlistProps) => {
    
    const list = wishlistItems.map((item) => {
        return (
            <WishlistItem 
                key={`${item.courseCode}.${item.courseName}`}
                item={item}
            />
        )
    })

    return (
        <div>

            <Stack spacing='xl' align='center'>
                <div>
                    {list}
                </div>
            </Stack>
        </div>
    );
}
 
export default Wishlist;