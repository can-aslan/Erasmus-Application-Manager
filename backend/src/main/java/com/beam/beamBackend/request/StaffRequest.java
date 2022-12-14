package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequest {
    private UUID userId;
    private Department department;
    private Faculty faculty;
}
