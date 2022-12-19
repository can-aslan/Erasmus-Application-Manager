import { Autocomplete, Badge, Button, Card, Center, Divider, Flex, Modal, SimpleGrid, Stack, Text, Title } from '@mantine/core';
import { IconDeviceFloppy, IconFile, IconPlus, IconSend } from '@tabler/icons';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';
import { toast } from 'react-toastify';
import { deleteWishItem, saveWishlist, submitWishlist } from '../../api/Student/CourseService';
import StatusFeedback from '../../components/cards/StatusFeedback';
import CourseTable from '../../components/tables/CourseTable';
import useAxiosSecure from '../../hooks/useAxiosSecure';
import { useCourses } from '../../hooks/useCourses';
import useHostCourses from '../../hooks/useHostCourses';
import { useStudentWishlist } from '../../hooks/useStudentWishlist';
import { useUser } from '../../provider/UserProvider';
import { BilkentCourse, Course, CourseWishlistItemMapping, HostCourse, NewCourseWish } from "../../types";
import ErrorPage from '../Feedback/ErrorPage';
import LoadingPage from '../Feedback/LoadingPage';

type CourseTableCourses = {
    bilkentCourse: BilkentCourse
    hostCourse: HostCourse
    wishId: string
}

const CourseWishlistPage = () => {
    const axiosSecure = useAxiosSecure()
    const queryClient = useQueryClient()
    const { user } = useUser()

    // Fetch bilkent courses
    const {data: bilkentCourses, isLoading: isBilkentCoursesLoading, isError: isBilkentCoursesError} = useCourses(axiosSecure)
    // Fetch host courses based on userId
    const {data: hostCourses, isLoading: isHostCoursesLoading, isError: isHostCoursesError } = useHostCourses(axiosSecure, user.bilkentId)
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
    const { mutate: saveMutation, isLoading: isSaveLoading } = useMutation({
        mutationKey: ['saveWishlist'],
        mutationFn: (wish: NewCourseWish) => saveWishlist(axiosSecure, user.bilkentId, wish),
        onSuccess: () => {
            queryClient.invalidateQueries(['wishlist']) // Refetch course wishlist on each save operation.
            toast.success("Successfully saved the wishlist!")
        },
        onError: () => toast.error("Oops. We couldn't save the wishlist. Please try again later.")
    })
    
    const { mutate: submitMutation, isLoading: isSubmitLoading } = useMutation({
        mutationKey: ['submitWishlist'],
        mutationFn: () => submitWishlist(axiosSecure, user.bilkentId),
        onSuccess: () => {
            queryClient.invalidateQueries(['wishlist'])
            toast.success("Wishlist has been submitted for the review of the coordinator.")
        },
        onError: () => toast.error("Oops. We couldn't submit the wishlist. Please try again later.")
    })

    const { mutate: deleteWishMutation, isLoading: isDeleteLoading } = useMutation({
        mutationKey: ['deleteWishItem'],
        mutationFn: (wishItemId: string) => deleteWishItem(axiosSecure, user.bilkentId, wishItemId),
        onSuccess: () => {
            queryClient.invalidateQueries(['wishlist'])
            toast.success("Successfully deleted the wishlist item.")
        },
        onError: () => toast.error("The item couldn't be deleted.")
    })


    if (isBilkentCoursesLoading || isHostCoursesLoading || isCourseWishlistLoading) {
        return <LoadingPage />
    }

    if (isBilkentCoursesError || isHostCoursesError) {
        return <ErrorPage message="We are having some problems accessing course data."/>
    }
    

    /*
        Autocomplete component accepts an array of strings or objects with value property to display them on the dropdown. 
        We chose the array of strings for this purpose and we are displaying course names.
    */
    const bilkentCoursesData = bilkentCourses.data.map((c) => c.courseName!)
    const hostCoursesData = hostCourses.data.map((h) => h.courseName!)

    // Table will consist of pairs of Bilkent courses and host uni courses. This is because
    // of the course transfer process that will happen later on. 
    const tableItems: Array<CourseTableCourses[]> | undefined = courseWishlist?.data.items ? courseWishlist.data.items.map((w) => {
        const courseTableCourses: Array<CourseTableCourses> = w.mappings.map(m => {
            const bilkentCourse: BilkentCourse = {
                bilkentCredit: w.bilkentCredits,
                courseCode: w.bilkentCourse,
                bilkentName: w.bilkentName,
                ects: w.ects,
            }

            const hostCourse: HostCourse = {
                courseCode: m.hostCourse,
                hostName: m.hostName,
                ects: m.ects || 0,
            }

            return {
                bilkentCourse,
                hostCourse,
                wishId: w.wishlistItemId,
            }
        })

        return courseTableCourses
    }) : []
    const flatTableItems: Array<CourseTableCourses> | undefined = tableItems ? tableItems.flat() : []
    
    const handleSave = () => {
        let uuid = "00000000-0000-0000-0000-000000000000"
        const courseWishlistItemMapping: CourseWishlistItemMapping[] = selectedHostCourses?.map((s, index) => {
            const hostCourse: HostCourse = hostCourses.data.find(h => h.courseName === s)!
            uuid = `${index}${uuid.substring((index / 10) + 1)}`
            return {
                hostCourse: hostCourse.courseCode,
                mappingItemId: uuid,
                ects: hostCourse.ects,
                hostName: hostCourse.courseName!,
            }   
        })

        const bilkentCourse: BilkentCourse = bilkentCourses.data.find(b => b.courseName === selectedBilkentCourse)!
        console.log(bilkentCourse)
        const newWish: NewCourseWish = {
            bilkentCourse: bilkentCourse.courseCode,
            bilkentCredits: bilkentCourse.bilkentCredit,
            ects: bilkentCourse.ects,
            bilkentName: bilkentCourse.courseName,
            mappings: [
                ...courseWishlistItemMapping
            ]
        }
        saveMutation(newWish)
        setSelectedHostCourses([])
        setSelectedBilkentCourse('')
        setSelectedBilkentCourse('')
    }
    
    const handleSubmit = async () => {
        submitMutation()
    }
    
    const handleRemoveWish = (e: React.MouseEvent, wishlistId: string): void => {
        deleteWishMutation(wishlistId)
    }

    const handleAddWish = () => {
        setSelectedHostCourses(prev => {
            return [
                ...prev,
                selectedHostCourse
            ]
        })
    }

    const badges = selectedHostCourses.map(s => {
        return <Badge color='red'>{selectedBilkentCourse} - {s}</Badge>
    })
    
    return (
        <Center>
            <Flex direction='column' align='center' gap={100}>
                <StatusFeedback 
                    title='Wishlist Status'
                    status={courseWishlist?.data.status || "WAITING"}
                />
                <Flex gap={100}>
                    <Flex direction='column' gap='xl'>
                        <Card miw={450} shadow='xl' radius='lg' p={36}>
                            <Flex direction='column' gap="xl">
                                <Title order={1} color='blue' mb={12}>Add a wish</Title>
                                <Autocomplete
                                    data={bilkentCoursesData}
                                    label='Corresponding Bilkent course'
                                    value={selectedBilkentCourse}
                                    onChange={(value) => {
                                        setSelectedHostCourse('')
                                        setSelectedHostCourses([])
                                        setSelectedBilkentCourse(value)
                                    }}
                                    placeholder="Corresponding course in Bilkent University"
                                    error={error}
                                />
                                <Autocomplete 
                                    data={hostCoursesData}
                                    label='Host course'
                                    value={selectedHostCourse}
                                    onChange={setSelectedHostCourse}
                                    placeholder="Host course you would like to take"
                                    error={error}
                                />
                                <Button 
                                    leftIcon={<IconPlus />} 
                                    size='md' 
                                    onClick={handleAddWish}
                                    disabled={selectedBilkentCourse === '' || selectedHostCourse === ''}
                                    >
                                    Add Course-Pair
                                </Button>
                                <SimpleGrid cols={1}>
                                    {badges || <Text color='dimmed'>Your course pairings will appear here</Text>}
                                </SimpleGrid>

                            </Flex>
                        </Card>
                        <Button onClick={handleSave} loading={isSaveLoading} leftIcon={<IconDeviceFloppy/>}>Save Pairings</Button>
                    </Flex>
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
                            mih={150}
                        >
                            <CourseTable
                                records={flatTableItems}
                                handleRemoveItem={handleRemoveWish}
                            />
                            <Flex gap='xl' align='center' justify='center'>
                                <Button 
                                    onClick={handleSubmit} 
                                    loading={isSubmitLoading} 
                                    color='red' 
                                    leftIcon={<IconSend />}
                                    disabled={selectedHostCourses.length !== 0}
                                >
                                        Submit for Approval
                                </Button>
                            </Flex>
                        </Flex>
                    </Card>
                </Flex>
            </Flex>
        </Center>
    );
}
 
export default CourseWishlistPage;