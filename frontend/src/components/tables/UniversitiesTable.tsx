import { ActionIcon, Grid, Stack, Text, TextInput } from "@mantine/core";
import { useDebouncedValue } from "@mantine/hooks";
import { IconListDetails, IconSearch } from "@tabler/icons";
import sortBy from 'lodash/sortBy';
import { DataTable, DataTableSortStatus } from "mantine-datatable";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { UniversityProxy } from "../../types";

interface UniversitiesTableProps {
    universities: Array<UniversityProxy>,
    isUniversitiesLoading: boolean,
}

const RECORDS_PER_PAGE_OPTIONS = [5, 10, 15, 20]

const UniversitiesTable = ({ universities, isUniversitiesLoading }: UniversitiesTableProps) => {
    // For pagination
    const [pageSize, setPageSize] = useState(RECORDS_PER_PAGE_OPTIONS[1])
    const [page, setPage] = useState(1)
    const [records, setRecords] = useState(sortBy(universities.slice(0, pageSize), 'universityName'))
    // For searching
    const [query, setQuery] = useState('')
    const [debouncedQuery] = useDebouncedValue(query, 200)
    // For sorting
    const [sortStatus, setSortStatus] = useState<DataTableSortStatus>({ columnAccessor: 'name', direction: 'asc' });

    const navigate = useNavigate()

    // For sorting
    useEffect(() => {
        const data = sortBy(universities, sortStatus.columnAccessor);
        setRecords(sortStatus.direction === 'desc' ? data.slice(0, pageSize).reverse() : data.slice(0, pageSize));
      }, [sortStatus]);

    // For pagination
    useEffect(() => {
        setPage(1)
    }, [pageSize])

    // For pagination
    useEffect(() => {
        const from = (page - 1) * pageSize
        const to = from + pageSize
        setRecords(universities.slice(from, to))
    }, [page, pageSize])

    // For search
    useEffect(() => {
        setRecords(
            universities.filter(({city, country, universityName, dormitory, specialCase, studentGrant}) => {
                if (debouncedQuery !== '' && 
                    !`${city} ${country} ${universityName} ${dormitory} ${studentGrant} ${specialCase}`
                    .trim()
                    .toLowerCase()
                    .includes(debouncedQuery.trim().toLowerCase())) {
                        return false
                    }

                    return true
            })
        )
    }, [debouncedQuery])

    return (
        <>
            <Grid align="center" mb="md">
                <Grid.Col xs={8} sm={9}>
                <TextInput
                    sx={{ flexBasis: '60%' }}
                    placeholder="Search universities"
                    icon={<IconSearch size={16} />}
                    value={query}
                    onChange={(e) => setQuery(e.currentTarget.value)}
                />
                </Grid.Col>
            </Grid>
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
                        sortable: true,
                    },
                    {
                        accessor: 'country',
                        title: "Country",
                        sortable: true,
                    },
                    {
                        accessor: 'city',
                        title: "City",
                        sortable: true,
                    },
                    {
                        accessor: 'dormitory',
                        title: "Dormitory",
                        sortable: true,
                        cellsStyle: ({ dormitory }) =>
                            dormitory ? {color: 'green'} : {color: 'red'},
                        render: ({ dormitory }) => dormitory ? "Offers dormitory" : "Does NOT offer dormitory"
                    },
                    {
                        accessor: 'studentGrant',
                        title: 'Student Grant',
                        sortable: true,
                        cellsStyle: ( { studentGrant } ) =>
                            studentGrant ? {color: 'green'} : {color: 'red'},
                        render: ({ studentGrant }) => studentGrant ? `${studentGrant}€` : "No student grants"
                    },
                    {
                        accessor: 'specialCase',
                        title: 'Special Requirements',
                        sortable: true,
                        cellsStyle: ({ specialCase }) =>
                            specialCase?.length === 0 ? {color: 'green'} : {color: 'red'},
                        render: ({ specialCase }) => specialCase?.length === 0 ? "No" : "Yes"
                    },
                    {
                        accessor: 'details',
                        title: 'Details',
                        render: (record) => (
                            <Link to={record.id}>
                                <ActionIcon
                                    color='blue'
                                >
                                    <IconListDetails />
                                </ActionIcon>
                            </Link>
                        )
                    },
                ]}
                records={records}
                withBorder
                withColumnBorders
                striped
                highlightOnHover
                noRecordsText="No universities to show."
                fetching={isUniversitiesLoading}
                page={page}
                totalRecords={universities.length}
                recordsPerPage={pageSize}
                onPageChange={(p) => setPage(p)}
                recordsPerPageOptions={RECORDS_PER_PAGE_OPTIONS}
                onRecordsPerPageChange={setPageSize}
                sortStatus={sortStatus}
                onSortStatusChange={setSortStatus}
                onRowClick={(record) => {
                    navigate(`/student/universities/${record.id}`)
                }}
            />
        </>
    );
}
 
export default UniversitiesTable;