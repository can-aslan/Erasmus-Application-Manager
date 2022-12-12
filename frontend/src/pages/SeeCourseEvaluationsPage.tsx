import { Flex, LoadingOverlay, Rating, Select, Text } from "@mantine/core";
import { IconBook } from "@tabler/icons";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { axiosSecure } from "../api/axios";
import { getPastCourseEvaluations } from "../api/SeeCourseEvaluationService";
import Evaluation from "../components/evaluation/Evaluation";
import SeeCourseEvaluation from "../components/evaluation/SeeCourseEvaluation";
import ErrorPage from "./Feedback/ErrorPage";
import LoadingPage from "./Feedback/LoadingPage";

interface SeeCourseEvaluationsPageProps {

}
const SeeCourseEvaluationsPage = () => {
    const [searchedCourse, setSearchedCourse] = useState('');
    const [selectedCourse, setSelectedCourse] = useState<string | null>(null);

    const availableCourses = [
        { value: "CS-201", label: "CS-201" },
        { value: "CS-315", label: "CS-315" },
        { value: "CS-319", label: "CS-319" },
        { value: "CS-202", label: "CS-202" },
        { value: "CS-101", label: "CS-101" },
    ];

    // const { data, isError, isLoading } = useQuery({
    //     queryFn: () => getPastCourseEvaluations(axiosSecure, "course id gelcek")
    // })
    
    // loading sayfası yerine overlay kullanalım 
    const isLoading = false;
    // if (isError || !data) {
    //     return (
    //         <ErrorPage />
    //     )
    // }
    // const evalList = data.data.eval_list;
    // const averageRating = data.data.average_rate;
    
    const averageRating = 4.5;
    const evalList = [{
        authorId: "2",
        rate: 2,
        comment: "string"
    }, {
        authorId: "2",
        rate: 3,
        comment: "stringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    },
    {
        authorId: "2",
        rate: 4.5,
        comment: "stringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    },
    {
        authorId: "2",
        rate: 4.5,
        comment: "stringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringstringAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
    },
    ]
    return (
        <>
        <LoadingOverlay visible={isLoading} overlayBlur={2} loader={<LoadingPage/>} />
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
                {selectedCourse != null && !isLoading && <Flex direction={"row"} gap={"sm"}>
                    <Text maw={500}> Average Rate of ({evalList.length} evaluations): </Text> <Rating emptySymbol={<IconBook></IconBook>} fullSymbol={<IconBook color="#1971c2" />} readOnly={true} fractions={2} value={averageRating}></Rating>
                </Flex>}
                {selectedCourse != null && !isLoading && <SeeCourseEvaluation evalList={evalList}></SeeCourseEvaluation>}
            </Flex>

        </>
    );
}

export default SeeCourseEvaluationsPage;