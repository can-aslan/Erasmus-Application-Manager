import { Autocomplete, Button, Card, Center, Divider, Flex, Modal, Stack, Title } from '@mantine/core';
import { IconDeviceFloppy, IconFile, IconPlus, IconSend } from '@tabler/icons';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { toast } from 'react-toastify';
import { saveWishlist, submitWishlist } from '../../api/Student/CourseService';
import StatusFeedback from '../../components/cards/StatusFeedback';
import CourseTable from '../../components/tables/CourseTable';
import useAxiosSecure from '../../hooks/useAxiosSecure';
import { useCourses } from '../../hooks/useCourses';
import useHostCourses from '../../hooks/useHostCourses';
import { useStudentWishlist } from '../../hooks/useStudentWishlist';
import { useUser } from '../../provider/UserProvider';
import { BilkentCourse, Course, CourseWishlist, CourseWishlistItem, HostCourse } from "../../types";
import ErrorPage from '../Feedback/ErrorPage';
import LoadingPage from '../Feedback/LoadingPage';

type CourseTableCourses = {
    bilkentCourse: BilkentCourse
    hostCourse: HostCourse
}

const CourseWishlistPage = () => {
    const axiosSecure = useAxiosSecure()
    const queryClient = useQueryClient()
    const { user } = useUser()

    // Fetch bilkent courses
    const {data: bilkentCourses, isLoading: isBilkentCoursesLoading, isError: isBilkentCoursesError} = useCourses(axiosSecure)
    // Fetch host courses based on userId
    const {data: hostCourses, isLoading: isHostCoursesLoading, isError: isHostCoursesError } = useHostCourses(axiosSecure, user.id)
    // Fetch the server state of course wishlist    
    const {data: courseWishlist, isLoading: isCourseWishlistLoading, isError: isCourseWishlistError} = useStudentWishlist(axiosSecure, user.bilkentId)

    // States are moved to here because the initial states depend on the properties being fetched
    const [selectedBilkentCourse, setSelectedBilkentCourse] = useState('')
    const [selectedHostCourse, setSelectedHostCourse] = useState('')
    const [selectedHostCourses, setSelectedHostCourses] = useState<Array<string>>([])
    const [error, setError] = useState(false)
    
    /*
        The wishlist is not automatically saved on each addition. Instead, the user is expected to save their 
        wishlist once they think, they have chosen enough courses. Once the user thinks that they don't want to
        choose any more courses, they will use the submit button to send their wishlist for coordinator approval.
    */
    const { mutate: save, isLoading: isSaveLoading } = useMutation({
        mutationKey: ['saveWishlist'],
        mutationFn: () => saveWishlist(axiosSecure, user.id, wishlistItems),
        onSuccess: () => {
            queryClient.invalidateQueries(['wishlist'])
            toast.success("Successfully saved the wishlist!")
        },
        onError: () => toast.error("Oops. We couldn't save the wishlist. Please try again later.")
    })
    
    const { mutate: submit, isLoading: isSubmitLoading } = useMutation({
        mutationKey: ['submitWishlist'],
        mutationFn: () => submitWishlist(axiosSecure, user.id, wishlistItems),
        onSuccess: () => {
            queryClient.invalidateQueries(['wishlist'])
            toast.success("Wishlist has been submitted for the review of the coordinator."),
        },
        onError: () => toast.error("Oops. We couldn't submit the wishlist. Please try again later.")
    })

    if (isBilkentCoursesLoading || isHostCoursesLoading || isCourseWishlistLoading) {
        return <LoadingPage />
    }

    if (isBilkentCoursesError || isHostCoursesError || isCourseWishlistError) {
        return <ErrorPage message="We are having some problems accessing course data."/>
    }
    

    /*
        Autocomplete component accepts an array of strings or objects with value property to display them on the dropdown. 
        We chose the array of strings for this purpose and we are displaying course names.
    */
    const bilkentCoursesData = bilkentCourses.data.map((c) => c.courseName)
    const hostCoursesData = hostCourses.data.map((h) => h.courseName)

    // Table will consist of pairs of Bilkent courses and host uni courses. This is because
    // of the course transfer process that will happen later on. 
    const tableItems: Array<CourseTableCourses> | undefined = wishlistItems?.map((w) => {
        return {
            bilkentCourse: w.correspondingBilkentCourse,
            hostCourse: w.otherUniCourses[0]
        }
    })
    
    const handleSave = () => {
        save()
    }
    
    const handleSubmit = () => {
        save()
        submit()
    }
    
    const handleRemoveWish = (e: React.MouseEvent, bilkentCourseId: string): void => {

    }

    const handleAddWish = () => {

    }
    
    return (
        <Center sx={{height: '65vh'}}>
            <Flex direction='column' align='center' gap={100}>
                <StatusFeedback 
                    title='Wishlist Status'
                    status={wishlist?.wishlistStatus || "WAITING"}
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