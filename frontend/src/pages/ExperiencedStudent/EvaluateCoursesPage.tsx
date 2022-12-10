import { Card, Center, Flex, Select } from "@mantine/core";
import { IconBook } from "@tabler/icons";
import { useState } from "react";
import Evaluation from "../../components/evaluation/Evaluation";
import { useCourses } from "../../hooks/useCourses";
import { Course } from "../../types";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";


const EvaluateCoursesPage = () => {
    const [searchedCourse, setSearchedCourse] = useState('');
    const [selectedCourse, setSelectedCourse] = useState<string | null>(null);
    const [currentEvaluation, setCurrentEvaluation] = useState("");
    const [givenRating, setGivenRating] = useState(0);
    const description = `You can evaluate ${selectedCourse} in terms of difficulty, assignments, instructors, exams, course content and material etc.`;
    //TODO: studentin eski evallerini de almak lazım, ona göre editable falan değişecek çünkü,
    // aşağıdakinden farklı bi query atmak gerekiyo bence o yüzden
    // Commented untill connected to database
    // // Fetch courses from the database
    // const { data: courses, isError: isCoursesError, isLoading: isCoursesLoading } = useCourses();
    // // Generate courses for the AutoComplete. AutoComplete requires the use of a field called value.

    // if (isCoursesLoading) {
    //     return (
    //         <LoadingPage />
    //     )
    // }

    // if (isCoursesError) {
    //     return (
    //         <ErrorPage />
    //     )
    // }
    // Generate courses for the Select. Select requires the use of a field called value and label.
    // Array<Course> değil de yeni bi tip??
    // const availableCourses: Array<Course> = courses.data.map((c) => {
    //     return {
    //         ...c,
    //         value: c.courseName
    //         label: c.courseName
    //     }
    // });
    // Mock data
    const availableCourses = [
        { value: "CS-201", label: "CS-201"},
        { value: "CS-315", label: "CS-315"},
        { value: "CS-319", label: "CS-319"},
        { value: "CS-202", label: "CS-202"},
        { value: "CS-101", label: "CS-101"},
    ];
    //TODO: selected course değişince ona bağlı olarak gözüken rating vs değişecek
    return (
        <>
            <Select
                searchable
                placeholder="Choose a course to evaluate"
                allowDeselect
                label={"Choose a course to evaluate"}
                nothingFound="No course found"
                onSearchChange={setSearchedCourse}
                onChange={setSelectedCourse}
                searchValue={searchedCourse}
                data={availableCourses} />
            {selectedCourse != null && <Evaluation givenRating={givenRating} evaluationName={selectedCourse!} description={description} currentEvaluation={currentEvaluation} setGivenRating={setGivenRating} setCurrentEvaluation={setCurrentEvaluation} emptySymbol={<IconBook/>} fullSymbol={<IconBook color="#1971c2"/>} editable={true}></Evaluation>}
        </>
    );
}

export default EvaluateCoursesPage;