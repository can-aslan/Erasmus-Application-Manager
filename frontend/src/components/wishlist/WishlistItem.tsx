import { Flex, Group, Modal, Text } from "@mantine/core";
import { useState } from 'react';
import { WishlistItemType } from "../../types";
import WishlistButton from "./WishlistButton";

interface WishlistItemProps {
    item: WishlistItemType,
    id: string,
}

const WishlistItem = ({ item }: WishlistItemProps) => {
    const [openModal, setOpenModal] = useState(false)

    const handleClick = () => {
        setOpenModal(true)
    }

    return (
        <div>
            <Modal
                opened={openModal}
                onClose={() => setOpenModal(false)}
                title={'Wish details'}
                padding='xl'
                centered={true}
            >
                <Flex gap='lg' direction='column'>
                    <Text weight={600}>Course code: {item.courseCode}</Text>
                    <Text weight={600}>Course name: {item.courseName}</Text>
                    <Text weight={600}>Bilkent credits: {item.bilkentCredits}</Text>
                    <Text weight={600}>ECTS credits: {item.ECTSCredits}</Text>
                </Flex>
            </Modal>
            <WishlistButton 
                onClick={handleClick}
            >
                <Group>
                    <Text 
                        size="lg" 
                        weight={500}
                    >
                        {item?.courseCode}: {item?.courseName}
                    </Text>
                </Group>
            </WishlistButton>
        </div>
    );
}
 
export default WishlistItem;