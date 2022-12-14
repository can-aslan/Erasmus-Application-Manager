import { Autocomplete, Button, Card, Center, Divider, Flex, Modal, Stack, Title } from '@mantine/core';
import { IconDeviceFloppy, IconFile, IconPlus, IconSend } from '@tabler/icons';
import { useMutation } from '@tanstack/react-query';
import { useState } from 'react';
import { toast } from 'react-toastify';
import { saveWishlist, submitWishlist } from '../../api/Student/CourseService';
import StatusFeedback from '../../components/cards/StatusFeedback';
import CourseTable from '../../components/tables/CourseTable';
import useAxiosSecure from '../../hooks/useAxiosSecure';
import { useUser } from '../../provider/UserProvider';
import { BilkentCourse, Course, CourseWishlist, CourseWishlistItem, HostCourse } from "../../types";
import { ResponseStudentSpecificCourseWishlist } from '../../types/responseTypes';

type CourseTableCourses = {
    bilkentCourse: BilkentCourse
    hostCourse: HostCourse
}

const CourseWishlistPage = () => {
    // TODO: Fetch initial wishlist.
    const [wishlist, setWishlist] = useState<CourseWishlist | null>(null)
    const [wishlistItems, setWishlistItems] = useState(wishlist?.wishlistItems)
    const [selectedBilkentCourse, setSelectedBilkentCourse] = useState('')
    const [selectedHostCourse, setSelectedHostCourse] = useState('')
    const [error, setError] = useState(false)
    const axiosSecure = useAxiosSecure()
    const { user } = useUser()
    
    // TODO: Fetch initial wishlist, fetch bilkent courses, fetch host courses
    const bilkentCourses: Array<BilkentCourse> = [
        {
            bilkentCredits: 10,
            ECTSCredits: 12,
            courseCode: "CS 200",
            courseName: "Hello ",
            courseUUID: "uuid",
            department: "CS",
        }
    ]

    const hostCourses: Array<HostCourse> = [
        {
            ECTSCredits: 12,
            courseCode: "CS 200",
            courseName: "Hello ",
            courseUUID: "uuid",
            department: "CS",
        }
    ]

    const bilkentCoursesData = bilkentCourses.map((c) => c.courseName)

    const hostCoursesData = hostCourses.map((h) => h.courseName)

    const handleAddWish = () => {
        setError(false)
        if (!selectedBilkentCourse || !selectedHostCourse ) {
            setError(true)
            return
        }


        setWishlistItems((prev) => {
            const bilkentCourse: BilkentCourse = bilkentCourses.find(b => b.courseName === selectedBilkentCourse)!
            const hostCourse: HostCourse = hostCourses.find(h => h.courseName === selectedHostCourse)!
            const newWishlistItem: CourseWishlistItem = {
                correspondingBilkentCourse: bilkentCourse,
                otherUniCourses: [hostCourse],
            }
            if (prev) {
                return [
                    ...prev,
                    newWishlistItem
                ]
            }
            else {
                return [
                    newWishlistItem
                ]
            }
        })
    }

    const handleRemoveWish = (e: React.MouseEvent, bilkentCourseId: string): void => {
        setWishlistItems(prev => prev?.filter(i => i.correspondingBilkentCourse.courseUUID !== bilkentCourseId))
    }

    const tableItems: Array<CourseTableCourses> | undefined = wishlistItems?.map((w) => {
        return {
            bilkentCourse: w.correspondingBilkentCourse,
            hostCourse: w.otherUniCourses[0]
        }
    })


    const { mutate: save, isLoading: isSaveLoading } = useMutation({
        mutationKey: ['saveWishlist'],
        mutationFn: () => saveWishlist(axiosSecure, user.id, wishlistItems),
        onSuccess: () => toast.success("Successfully saved the wishlist!"),
        onError: () => toast.error("Oops. We couldn't save the wishlist. Please try again later.")
    })

    const { mutate: submit, isLoading: isSubmitLoading } = useMutation({
        mutationKey: ['submitWishlist'],
        mutationFn: () => submitWishlist(axiosSecure, user.id, wishlistItems),
        onSuccess: () => toast.success("Wishlist has been submitted for the review of the coordinator."),
        onError: () => toast.error("Oops. We couldn't submit the wishlist. Please try again later.")
    })

    const handleSave = () => {
        save()
    }

    const handleSubmit = () => {
        submit()
    }

    return (
        <Center sx={{height: '65vh'}}>
            <Flex direction='column' align='center' gap={100}>
                <StatusFeedback 
                    title='Wishlist Status'
                    status={wishlist?.wishlistStatus || "PENDING"}
                />
                <Flex gap={100}>
                    <Card miw={400} shadow='xl' radius='lg' p={36}>
                        <Flex direction='column' gap="xl">
                            <Title order={1} color='blue' mb={12}>Add a wish</Title>
                            <Autocomplete 
                                data={hostCoursesData}
                                label='Host course'
                                value={selectedHostCourse}
                                onChange={setSelectedHostCourse}
                                placeholder="Host course you would like to take"
                                error={error}
                            />
                            <Autocomplete
                                data={bilkentCoursesData}
                                label='Corresponding Bilkent course'
                                value={selectedBilkentCourse}
                                onChange={setSelectedBilkentCourse}
                                placeholder="Corresponding course in Bilkent University"
                                error={error}
                            />
                            <Button leftIcon={<IconPlus />} size='md' onClick={handleAddWish}>Add</Button>
                        </Flex>
                    </Card>
                    <Divider orientation='vertical' />
                    <Card 
                        miw={700} 
                        shadow='xl' 
                        radius='lg' 
                        p={36}
                    >
                        <Flex
                            direction='column'
                            gap='xl'
                        >
                            <CourseTable
                                records={tableItems}
                                handleRemoveItem={handleRemoveWish}
                            />
                            <Flex gap='xl' align='center' justify='center'>
                                <Button onClick={handleSave} loading={isSaveLoading} leftIcon={<IconDeviceFloppy/>}>Save</Button>
                                <Button onClick={handleSubmit} loading={isSubmitLoading} color='red' leftIcon={<IconSend />}>Submit for Approval</Button>
                            </Flex>
                        </Flex>
                    </Card>
                </Flex>
            </Flex>
        </Center>
    );
}
 
export default CourseWishlistPage;