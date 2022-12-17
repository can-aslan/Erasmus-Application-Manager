import { ActionIcon } from "@mantine/core";
import { IconTrash } from "@tabler/icons";
import { DataTable } from "mantine-datatable";
import React from "react";
import { BilkentCourse, HostCourse } from "../../types";

type CourseTableCourses = {
    bilkentCourse: BilkentCourse
    hostCourse: HostCourse
}

interface CourseTableProps {
    records: Array<CourseTableCourses> | undefined,
    handleRemoveItem: (event: React.MouseEvent, bilkentCourseId: string) => void
}

const CourseTable = ({ records, handleRemoveItem }: CourseTableProps) => {
    return (
        <DataTable
            mih={120}
            columns={[
                {
                    accessor: 'bilkentCourse.courseCode',
                    title: "Bilkent Course Code",
                    width: 80
                },
                {
                    accessor: 'bilkentCourse.courseName',
                    title: "Bilkent Course Name",
                    width: 120
                },
                {
                    accessor: 'bilkentCourse.ECTSCredits',
                    title: "ECTS Credits",
                    width: 120
                },
                {
                    accessor: 'hostCourse.courseCode',
                    title: "Host Course Code",
                    width: 80
                },
                {
                    accessor: 'hostCourse.courseName',
                    title: "Host Course Name",
                    width: 120
                },
                {
                    accessor: 'hostCourse.ECTSCredits',
                    title: "ECTS Credits",
                    width: 120
                },
                {
                    title: "Action",
                    accessor: "actions",
                    textAlignment: 'right',
                    render: (course) => (
                        <ActionIcon onClick={(e) => handleRemoveItem(e, course.bilkentCourse.courseId)} color='red' size='xl'>
                            <IconTrash />
                        </ActionIcon>
                    )
                }
            ]}
            records={records}
            withBorder
            withColumnBorders
            striped
            highlightOnHover
            noRecordsText="No course items to show."
        />
    );
}
 
export default CourseTable;