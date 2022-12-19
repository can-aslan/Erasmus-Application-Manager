import { Button, Center, Flex, Select } from "@mantine/core";
import { useMutation } from "@tanstack/react-query";
import { useState } from 'react';
import { toast } from "react-toastify";
import { placeStudents } from "../../api/PlacementService";
import useAxiosSecure from "../../hooks/useAxiosSecure";
import { DEPARTMENTS } from "../../utils/constants";

const PlacementPage = () => {
    const axiosSecure = useAxiosSecure()
    const [selectedDepartment, setSelectedDepartment] = useState('')

    const {mutate: placementMutate, isLoading: isPlacementLoading} = useMutation({
        mutationKey: ['studentPlacement'],
        mutationFn: (department: string) => placeStudents(axiosSecure, department),
    })

    const handlePlacement = () => {
        const department = DEPARTMENTS.find(d => d.value === selectedDepartment)
        const value = department?.value
        if (value ) {
            placementMutate(value)
        }
        else {
            toast.error("You should choose a department first")
        }
    }
    
    return (
        <Center >
            <Flex direction='column' miw={500} gap='xl'>
                
                <Select
                    label="Select department:"
                    data={DEPARTMENTS}
                    value={selectedDepartment}
                    onChange={(value) => setSelectedDepartment(value!)}
                >

                </Select>
                <Button
                    onClick={handlePlacement}
                    loading={isPlacementLoading}
                >
                    Place Students
                </Button>
            </Flex>
        </Center>
    );
}
 
export default PlacementPage;