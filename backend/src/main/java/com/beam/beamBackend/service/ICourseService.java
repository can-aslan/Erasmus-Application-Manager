package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.HostCourse;
import com.beam.beamBackend.request.ApprovedCourse;

public interface ICourseService {
    BilkentCourse addBilkentCourse(BilkentCourse bilkentCourse) throws Exception;
    BilkentCourse getBilkentCourseById(UUID courseId) throws Exception;
    HostCourse addHostCourse(HostCourse hostCourse) throws Exception;
    HostCourse getHostCourseById(UUID courseId) throws Exception;
    // List<HostCourse> getHostCourseByUniId(UUID uniId) throws Exception;
    List<BilkentCourse> getBilkentCourseByDepartment(Department department) throws Exception;
    List<BilkentCourse> getAllBilkentCourse() throws Exception;
    List<HostCourse> getAllHostCourse() throws Exception;
    // BilkentCourse addApprovedCourse(ApprovedCourse approvedCourse) throws Exception;
    // List<Object> getAllApprovedCoursesInUni(UUID hostUniId) throws Exception;
}
