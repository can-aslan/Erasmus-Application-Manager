import { useQuery } from "@tanstack/react-query";
import { AxiosInstance } from "axios";
import { getAllStudents } from "../api/StudentService";

const useAllStudents = (axios: AxiosInstance) => {
    return useQuery({
        queryKey: ['getAllStudents'],
        queryFn: () => getAllStudents(axios)
    })
}
 
export default useAllStudents;