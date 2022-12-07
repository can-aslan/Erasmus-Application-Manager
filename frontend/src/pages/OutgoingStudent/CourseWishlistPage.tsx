import { Autocomplete, Button, Card, Center, Flex, Modal, Stack, Title } from '@mantine/core';
import { IconPlus } from '@tabler/icons';
import { useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { getCourseWishlist } from '../../api/Student/CourseService';
import Wishlist from "../../components/wishlist/Wishlist";
import { useUser } from '../../provider/UserProvider';
import { WishlistItemType } from "../../types";
import ErrorPage from '../ErrorPage';
import LoadingPage from '../LoadingPage';

const CourseWishlistPage = () => {
    const { user } = useUser()
    const { data: wishlist, isError, isLoading } = useQuery({
        queryFn: () => getCourseWishlist(user?.uuid!)
    })

    if (isLoading) {
        return (
            <LoadingPage />
        )
    }

    if (isError && !wishlist) {
        return (
            <ErrorPage />
        )
    }

    const [openModal, setOpenModal] = useState(false)
    const [selectedCourse, setSelectedCourse] = useState('')
    const [deletedItems, setDeletedItems] = useState<Array<WishlistItemType>>([])
    const [newItems, setNewItems] = useState<Array<WishlistItemType>>([])
    const [wishlistItems, setWishlistItems] = useState<Array<WishlistItemType>>(wishlist) 
    // TODO: Fetch available courses from the database
    
    const availableCourses = [
        {
            value: 'CS 101',
            uuid: 'aaabbb',
            ECTSCredits: 5,
            bilkentCredits: 3,
            courseName: 'Intro to Programming I',
            courseCode: 'CS 101',
        },
        {
            value: 'CS 102',
            uuid: 'aaabbb',
            ECTSCredits: 5,
            bilkentCredits: 3,
            courseName: 'Intro to Programming II',
            courseCode: 'CS 102',
        }
    ]

    const handleDeleteWish = (e: React.MouseEvent) => {
        const id = e.currentTarget.id
        setDeletedItems(wishlistItems.filter((w) => w.uuid === id))
        setWishlistItems(wishlistItems.filter((w) => w.uuid !== id))
        setNewItems(newItems.filter((n => n.uuid !== id)))
    }

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
        setNewItems((prev) => {
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
            <Flex direction='column' gap='xl'>
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
                        <Wishlist wishlistItems={wishlistItems} handleDeleteWish={handleDeleteWish}/>
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
                <Button disabled={newItems.length === 0 && deletedItems.length === 0}>Save</Button>
            </Flex>
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