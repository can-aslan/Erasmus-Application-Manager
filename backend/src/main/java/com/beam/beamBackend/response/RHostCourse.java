package com.beam.beamBackend.response;

import java.util.UUID;

import com.beam.beamBackend.model.HostCourse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RHostCourse {
    private String courseCode;
    private String courseName;
    private double ects;
    private String syllabus;
    private String webPage;
    private String hostName;
    private UUID universityId;

    public RHostCourse(HostCourse hostCourse) {
        this.courseCode = hostCourse.getCourseCode();
        this.courseName = hostCourse.getCourseName();
        this.ects = hostCourse.getEcts();
        this.syllabus = hostCourse.getSyllabus();
        this.webPage = hostCourse.getWebPage();
        this.hostName = hostCourse.getUniversity().getName();
        this.universityId = hostCourse.getUniversity().getId();
    }
}
