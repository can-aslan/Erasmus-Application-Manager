import { Autocomplete, Center, FileInput, Flex } from "@mantine/core";
import { IconUpload } from "@tabler/icons";
import { useState } from "react";
import { CoordinatorAssociatedStudents, StudentAssociatedCourse } from "../../types";

const TranscriptUploadPage = () => {
    const [selectedCoordinatorValue, setSelectedCoordinatorValue] = useState('')
    const [selectedStudentValue, setSelectedStudentValue] = useState('')
    const [selectedStudentIndex, setSelectedStudentIndex] = useState(0)
    const [transcriptFile, setTranscriptFile] = useState<File | null>(null)
    
    // TODO: Fetch coordinator list
    
    // TODO: Fetch students based on coordinator
    const students: Array<CoordinatorAssociatedStudents> = [
        {
            studentUuid: "student1",
            file: "file",
            formUuid: "form1",
            studentName: "Selim Can Güler",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
        {
            studentUuid: "student1",
            file: "file",
            formUuid: "form1",
            studentName: "Ahmet",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
        {
            studentUuid: "student1",
            file: "file",
            formUuid: "form1",
            studentName: "Mahmut",
            studentSurname: "Güler",
            studentId: "22002811",
            studentDepartment: ["CS"],
        },
    ]
    const studentsData = students.map((s) => {
        return {
            ...s,
            value: `${s.studentName} - ${s.studentId}`,
        }
    })
    
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
                    onChange={((value) => {
                        students.map((s, index) => {
                            value.includes(s.studentName) && value.includes(s.studentId) ? setSelectedStudentIndex(index) : ''
                        })
                        setSelectedStudentValue(value)
                    })}
                    disabled={!selectedCoordinatorValue}
                />
                <FileInput
                    placeholder="Pick transcript"
                    label="Transcript"
                    withAsterisk
                    value={transcriptFile}
                    onChange={setTranscriptFile}
                    accept={'application/pdf'}
                    icon={<IconUpload />}
                    disabled={!selectedCoordinatorValue}
                />
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