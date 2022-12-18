import { Autocomplete, Button, Center, FileInput, Flex } from "@mantine/core";
import { IconUpload } from "@tabler/icons";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { toast } from "react-toastify";
import { submitFile } from "../../api/FileService";
import useAllStudents from "../../hooks/useAllStudents";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { CoordinatorAssociatedStudents, StudentAssociatedCourse } from "../../types";
import ErrorPage from "../Feedback/ErrorPage";
import LoadingPage from "../Feedback/LoadingPage";

interface TranscriptSubmitInterface {
    formData: FormData,
    studentId: string,
}

const TranscriptUploadPage = () => {
    const [selectedStudentValue, setSelectedStudentValue] = useState('')
    const [transcriptFile, setTranscriptFile] = useState<File | null>(null)
    const axiosSecure = useAxiosSecure()
    
    // Fetch students
    const { data: allStudents, isLoading: isStudentsLoading, isError: isStudentsError } = useAllStudents(axiosSecure)

    const { mutate: transcriptUploadMutation } = useMutation({
        mutationKey: ['submitTranscriptForm'],
        mutationFn: (data: TranscriptSubmitInterface) => submitFile(axiosSecure, data.formData, data.studentId),
        onSuccess: () => toast.success(`Submitted the transcript for student: ${selectedStudentValue}`),
        onError: () => toast.error(`Couldn't submit the transcript for student: ${selectedStudentValue}`),
    })

    if (isStudentsLoading) {
        return <LoadingPage message="Fetching student details..."/>
    }

    if (isStudentsError) {
        return <ErrorPage message="We have encountered with an error while fetching the students"/>
    }

    const studentsData = allStudents.map((s) => `${s.user.name} - ${s.user.bilkentId}`)

    const handleTranscriptUpload = () => {
        if (transcriptFile) {
            const formData = new FormData()
            const student = allStudents.find(s => selectedStudentValue.includes(`${s.user.name} - ${s.user.bilkentId}`))
            const studentId = student?.user.id!

            formData.append("file", transcriptFile)
            
            const mutationData = {
                formData,
                studentId
            }
            transcriptUploadMutation(mutationData)
        }
        else {
            toast.error("Please choose a transcript first.")
        }
    }
    
    return (
        <Center>
            <Flex 
                miw={500}
                maw={600} 
                gap='xl' 
                direction='column'
                justify='center'
            >
                <Autocomplete
                    label={'Student:'}
                    data={studentsData}
                    placeholder='Select a student'
                    value={selectedStudentValue}
                    onChange={setSelectedStudentValue}
                />
                <FileInput
                    placeholder="Pick transcript"
                    label="Transcript"
                    withAsterisk
                    value={transcriptFile}
                    onChange={setTranscriptFile}
                    accept={'application/pdf'}
                    icon={<IconUpload />}
                    disabled={!selectedStudentValue}
                />
                <Button
                    disabled={!transcriptFile}
                    onClick={handleTranscriptUpload}
                >
                    Submit Transcript
                </Button>
                {transcriptFile && 
                    <object
                        data={URL.createObjectURL(transcriptFile)}
                        type='application/pdf'
                        width='600px'
                        height='600px'
                    >/
                    </object>
                }
            </Flex>


        </Center>
    );
}
 
export default TranscriptUploadPage;