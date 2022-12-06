import useAxiosSecure from "../../hooks/useAxiosSecure"
import { Course, StudentAssociatedCourse } from "../../types"

const axiosSecure = useAxiosSecure()

export const getAllStudentWishlists = async (coordinatorId: string) => {
    const response = axiosSecure.get<Array<StudentAssociatedCourse>>(`/api/coordinator/studentWishlists/${coordinatorId}`)
    return response
}