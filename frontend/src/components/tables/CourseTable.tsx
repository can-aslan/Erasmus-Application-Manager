import { ActionIcon } from "@mantine/core";
import { IconTrash } from "@tabler/icons";
import { DataTable } from "mantine-datatable";
import React from "react";
import { BilkentCourse, HostCourse } from "../../types";

type CourseTableCourses = {
    bilkentCourse: BilkentCourse
    hostCourse: HostCourse
    wishId: string,
}

interface CourseTableProps {
    records: Array<CourseTableCourses> | undefined,
    handleRemoveItem: (event: React.MouseEvent, wishlistId: string) => void
}

const CourseTable = ({ records, handleRemoveItem }: CourseTableProps) => {
    return (
        <DataTable
            minHeight={150}
            columns={[
                {
                    accessor: 'bilkentCourse.courseCode',
                    title: "Bilkent Course Code",
                    width: 80
                },
                {
                    accessor: 'bilkentCourse.bilkentName',
                    title: "Bilkent Course Name",
                    width: 120
                },
                {
                    accessor: 'bilkentCourse.ects',
                    title: "ECTS Credits",
                    width: 120
                },
                {
                    accessor: 'hostCourse.courseCode',
                    title: "Host Course Code",
                    width: 80
                },
                {
                    accessor: 'hostCourse.hostName',
                    title: "Host Course Name",
                    width: 120
                },
                {
                    accessor: 'hostCourse.ects',
                    title: "ECTS Credits",
                    width: 120
                },
                {
                    title: "Action",
                    accessor: "actions",
                    textAlignment: 'right',
                    render: (course) => (
                        <ActionIcon onClick={(e) => handleRemoveItem(e, course.wishId)} color='red' size='xl'>
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