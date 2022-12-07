import { Autocomplete, Button, Card, Center, Flex, Modal, Stack, Title } from '@mantine/core';
import { IconPlus } from '@tabler/icons';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import { getCourses, getCourseWishlist, saveWishlist } from '../../api/Student/CourseService';
import Wishlist from "../../components/wishlist/Wishlist";
import { useCourses } from '../../hooks/useCourses';
import { useSaveWishlist } from '../../hooks/useSaveWishlist';
import { useStudentWishlist } from '../../hooks/useStudentWishlist';
import { useUser } from '../../provider/UserProvider';
import { WishlistItemType } from "../../types";
import ErrorPage from '../Feedback/ErrorPage';
import LoadingPage from '../Feedback/LoadingPage';

const CourseWishlistPage = () => {
    const [openModal, setOpenModal] = useState(false)
    const [selectedCourse, setSelectedCourse] = useState('')
    const [deletedItems, setDeletedItems] = useState<Array<WishlistItemType>>([])
    const [newItems, setNewItems] = useState<Array<WishlistItemType>>([])
    const { user } = useUser()
    
    // Fetch student's wishlist
    const { data: wishlist, isError: isWishlistError, isLoading: isWishlistLoading } = useStudentWishlist(user!.id)
    const [wishlistItems, setWishlistItems] = useState<Array<WishlistItemType>>(wishlist || []) 

    // Fetch available courses from the database
    const { data: courses, isError: isCoursesError, isLoading: isCoursesLoading } = useCourses()

    // Mutation for saving the wishlist to database
    const { data: savedWishlist, isError: isSaveWishlistError, isLoading: isSaveWishlistLoading, mutate} = useSaveWishlist(wishlistItems)

    if (isWishlistLoading|| isCoursesLoading) {
        return (
            <LoadingPage />
        )
    }

    if (isWishlistError || isCoursesError) {
        return (
            <ErrorPage />
        )
    }
    
    // TODO: Use 'courses' (fetched from the backend) instead of available courses
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

    const handleWishlistSave = () => {
        mutate()
    }

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
                <Button 
                    disabled={newItems.length === 0 && deletedItems.length === 0}
                    loading={isSaveWishlistLoading}
                    onClick={handleWishlistSave}
                >
                    Save
                </Button>
                {/* TODO: Report error in case of saving wishlist is unsuccessful: 'isSaveWishlistError' */}
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