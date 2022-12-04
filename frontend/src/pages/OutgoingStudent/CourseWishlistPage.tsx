import { Autocomplete, Button, Card, Center, Flex, Modal, Stack, Title } from '@mantine/core';
import { IconPlus } from '@tabler/icons';
import { useState } from 'react';
import Wishlist from "../../components/wishlist/Wishlist";
import { WishlistItemType } from "../../types";

const CourseWishlistPage = () => {
    // TODO: Add save button. Enable it in the case there are unsaved changes. Keep track of unsaved
    // changes by using newItems. Add to new items on each new wish addition

    // TODO: Fetch already existing wishlist items from the database.
    const [openModal, setOpenModal] = useState(false)
    const [selectedCourse, setSelectedCourse] = useState('')
    const [newItems, setNewItems] = useState(Array<WishlistItemType>)
    const [wishlistItems, setWishlistItems] = useState<Array<WishlistItemType>>([
        {
            ECTSCredits: 5,
            bilkentCredits: 3,
            courseName: 'Intro to Programming',
            courseCode: 'CS 101',
        },
        {
            ECTSCredits: 5,
            bilkentCredits: 3,
            courseName: 'Intro to Programming',
            courseCode: 'CS 101',
        }
    ]) 
    // TODO: Fetch available courses from the database
    const availableCourses = [
        {
            value: 'CS 101',
            ECTSCredits: 5,
            bilkentCredits: 3,
            courseName: 'Intro to Programming',
            courseCode: 'CS 101',
        },
        {
            value: 'CS 102',
            ECTSCredits: 5,
            bilkentCredits: 3,
            courseName: 'Intro to Programming',
            courseCode: 'CS 102',
        }
    ]

    const handleAddWish = () => {
        const selected = availableCourses.find((c) => c.value === selectedCourse)
        if (!selected) {
            setOpenModal(false)
            return
        }
        
        setWishlistItems((prev) => {
            return (
                [
                    ...prev,
                    selected
                ]
            )
        })
        setOpenModal(false)
    }

    return (
        <Center sx={{height: '50vh'}}>
            <Card
                p='xl'
                shadow='lg'
                radius='lg'
                sx={(theme) => ({
                    bacgroundColor: theme.colorScheme === 'dark' ? theme.colors.dark[5] : theme.colors.gray[1],
                })}
                
            >
                <Flex gap={24} direction='column' align='center'>
                    <Title order={2}>
                        Your Wishlist
                    </Title>
                    <Wishlist wishlistItems={wishlistItems}/>
                    <div>
                        <Button
                            onClick={() => setOpenModal(true)}
                            leftIcon={<IconPlus />}
                        >
                            Add New Wish
                        </Button>
                    </div>
                </Flex>
            </Card>
            <Modal
                opened={openModal}
                onClose={() => setOpenModal(false)}
                title={'Add new course'}
                padding='xl'
                centered={true}
            >
                <Stack align='center' spacing='lg'>
                    <Autocomplete 
                        data={availableCourses}
                        value={selectedCourse}
                        onChange={setSelectedCourse}
                        defaultValue={''}
                        placeholder={'Select a course'}
                    />
                    <Button size='md' onClick={handleAddWish}>Add</Button>
                </Stack>
            </Modal>
        </Center>
    );
}
 
export default CourseWishlistPage;