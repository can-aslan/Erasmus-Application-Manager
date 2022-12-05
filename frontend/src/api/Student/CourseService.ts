import useAxiosSecure from "../../hooks/useAxiosSecure"

const axiosSecure = useAxiosSecure()

// TODO: Query: get previously requested courses
export const getPreviouslyRequestedCourses = () => {
    const response = axiosSecure.get('/api/student/getPreviouslyRequestedCourses', {

    })
}

// TODO: Query: Get all courses
export const getCourses = async () => {

}

// TODO: Query: Get Bilkent or any other school courses
export const getSchoolCourses = async () => {

}

// TODO: Mutation: Make course request
export const makeCourseRequest = async () => {

}

// TODO: Mutation: Save wishlist
export const saveWishlist = async () => {
    
}