package com.beam.beamBackend.response;

import com.beam.beamBackend.model.Staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RRegisterStaff {
    private Staff staff;
    private RInstructorCourseAdd instructorCourses;
}
