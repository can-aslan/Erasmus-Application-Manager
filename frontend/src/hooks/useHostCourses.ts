import { useQuery } from "@tanstack/react-query";
import { AxiosInstance } from "axios";
import { getUniCourses, getUniCoursesByUserId } from "../api/Student/CourseService";

const useHostCourses = (axios: AxiosInstance, uniId: string) => {
    return useQuery({
        queryKey: ['hostUniCourses'],
        queryFn: () => getUniCoursesByUserId(axios, uniId)
    })
}
 
export default useHostCourses;