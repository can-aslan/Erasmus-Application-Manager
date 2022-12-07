import { Autocomplete, Button, CloseButton, Flex, Modal, Stack, Text } from "@mantine/core";
import { WishlistItemType } from "../../types";
import WishlistItem from './WishlistItem';

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
                    {list.length !== 0 ? list : <Text color='dimmed'>Your wishlist appears to be empty. <br />Try adding a new wish with the button below!</Text>}
                </div>
            </Stack>
        </div>
    );
}
 
export default Wishlist;