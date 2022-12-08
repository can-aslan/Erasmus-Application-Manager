import { DataTable } from "mantine-datatable";
import { UniversityProxy } from "../../types";

interface UniversitiesTableProps {
    records: Array<UniversityProxy>,
}

const UniversitiesTable = ({ records }: UniversitiesTableProps) => {
    return (
        <DataTable
            columns={[
                {
                    accessor: 'index',
                    title: '#',
                    textAlignment: 'left',
                    width: 40,
                    render: (record) => records?.indexOf(record) + 1
                },
                {
                    accessor: 'universityName',
                    title: 'University',
                },
                {
                    accessor: 'country',
                    title: "Country",
                },
                {
                    accessor: 'city',
                    title: "City",
                },
                {
                    accessor: 'dormitory',
                    title: "Dormitory"
                },
                {
                    accessor: 'studentGrant',
                    title: 'Student Grant',
                    cellsStyle: ( { studentGrant } ) =>
                        studentGrant ? {color: 'green'} : {color: 'red'},
                    render: ({ studentGrant }) => studentGrant ? `${studentGrant}€` : "No student grants"
                },
                {
                    accessor: 'specialCase',
                    title: 'Special Requirements',
                    render: ({ specialCase }) => specialCase?.length === 0 ? "No" : "Yes"
                }
            ]}
            records={records}
            withBorder
            withColumnBorders
            striped
            highlightOnHover
        />
    );
}
 
export default UniversitiesTable;