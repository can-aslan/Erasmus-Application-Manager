import { Flex, LoadingOverlay, Rating, Select, Text } from "@mantine/core";
import { IconBook } from "@tabler/icons";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { getPastCourseEvaluations } from "../api/SeeCourseEvaluationService";
import { getUniCourses } from "../api/Student/CourseService";
import Evaluation from "../components/evaluation/Evaluation";
import SeeCourseEvaluation from "../components/evaluation/SeeCourseEvaluation";
import useAxiosSecure from "../hooks/useAxiosSecure";
import ErrorPage from "./Feedback/ErrorPage";
import LoadingPage from "./Feedback/LoadingPage";

interface SeeCourseEvaluationsPageProps {
    uniId: string
}
const SeeCourseEvaluationsPage = ({uniId}:SeeCourseEvaluationsPageProps) => {
    const [searchedCourse, setSearchedCourse] = useState('');
    const [selectedCourse, setSelectedCourse] = useState<string | null>(null);
    const axiosSecure = useAxiosSecure();
    
    // en basta course listesi al
    // const { data: courseData, isError: isCourseError, isLoading: isCourseLoading } = useQuery({
    //     queryFn: () => getUniCourses(axiosSecure, uniId)
    // })
    
    const availableCourses = [
        { value: "CS-201", label: "CS-201", courseId: "4520" },
        { value: "CS-315", label: "CS-315", courseId: "452543" },
        { value: "CS-319", label: "CS-319", courseId: "7845" },
        { value: "CS-202", label: "CS-202", courseId: "4" },
        { value: "CS-101", label: "CS-101", courseId: "12" },
    ];

    const { data: evalData, mutate, isError: isEvalError, isLoading: isEvalLoading } = useMutation({
        mutationFn: (courseId: string) => getPastCourseEvaluations(axiosSecure, courseId),
        mutationKey: ["course_eval"]
    })

    // useEffect(() => {
    //     mutate(availableCourses.find(courseCode === selectedCourse).courseId)
    // }, [selectedCourse]);
    
    // if (isCourseLoading){
    //     return (
    //         <LoadingPage />
    //     )
    // }
    
    // if (isEvalError || isCourseError) {
    //     return (
    //         <ErrorPage />
    //     )
    // }

    if (isEvalError) {
        return (
            <ErrorPage />
        )
    }
    const evalList = evalData?.data.eval_list;
    const averageRating = evalData?.data.average_rate;

    // Mock data
    // const averageRating = 4.5;
    // const evalList = [{
    //     authorId: "2",
    //     rate: 2,
    //     comment: "string"
    // }, {
    //     authorId: "2",
    //     rate: 3,
    //     comment: "stringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    // },
    // {
    //     authorId: "2",
    //     rate: 4.5,
    //     comment: "stringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    // },
    // {
    //     authorId: "2",
    //     rate: 4.5,
    //     comment: "stringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    // },
    // ]
    return (
        <>
            <LoadingOverlay visible={isEvalLoading} overlayBlur={2} loader={<LoadingPage />} />
            <Flex direction={"column"} gap={"sm"}>
                <Select
                    searchable
                    placeholder="Choose a course see the evaluations"
                    allowDeselect
                    label={"Choose a course to see the evaluations"}
                    nothingFound="No course found"
                    onSearchChange={setSearchedCourse}
                    onChange={setSelectedCourse}
                    searchValue={searchedCourse}
                    data={availableCourses} />
                {selectedCourse != null && !isEvalLoading && <Flex direction={"row"} gap={"sm"}>
                    <Text maw={500}> Average Rate of ({evalList?.length} evaluations): </Text> <Rating emptySymbol={<IconBook></IconBook>} fullSymbol={<IconBook color="#1971c2" />} readOnly={true} fractions={2} value={averageRating}></Rating>
                </Flex>}
                {selectedCourse != null && !isEvalLoading && <SeeCourseEvaluation evalList={evalList!}></SeeCourseEvaluation>}
            </Flex>

        </>
    );
}

export default SeeCourseEvaluationsPage;