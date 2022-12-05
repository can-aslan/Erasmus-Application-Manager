import { Autocomplete, Button, CloseButton, Flex, Modal, Stack } from "@mantine/core";
import { IconPlus } from '@tabler/icons';
import { useState } from 'react';
import { WishlistItemType } from "../../types";
import WishlistItem from "./WishlistItem";

interface WishlistProps {
    wishlistItems: Array<WishlistItemType>,
    handleDeleteWish: React.MouseEventHandler,
}

const Wishlist = ({ wishlistItems, handleDeleteWish }: WishlistProps) => {
    const list = wishlistItems.map((item) => {
        return (
            <Flex gap='md' align='center'>
                <WishlistItem 
                    id={item.uuid}
                    key={`${item.courseCode}.${item.courseName}`}
                    item={item}
                />
                <CloseButton 
                    id={item.uuid}
                    title="Delete Wish"
                    color='red'
                    size="xl"
                    iconSize={20}
                    onClick={handleDeleteWish}    
                />   
            </Flex>
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