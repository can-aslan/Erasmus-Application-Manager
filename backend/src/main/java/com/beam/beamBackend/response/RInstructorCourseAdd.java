package com.beam.beamBackend.response;

import java.util.List;
import java.util.Set;

import com.beam.beamBackend.model.InstructorCourse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RInstructorCourseAdd {
    private List<InstructorCourse> instructorCourseList;
    private Set<String> coursesNotAdded;
}
