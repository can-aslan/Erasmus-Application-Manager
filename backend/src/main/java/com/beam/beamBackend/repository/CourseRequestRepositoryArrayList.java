package com.beam.beamBackend.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.CourseRequest;

@Qualifier("courseRequest-arrayList")
@Repository
public class CourseRequestRepositoryArrayList // implements ICourseRequestRepository
{
    private static List<CourseRequest> DB = new ArrayList<CourseRequest>();

    // @Override
    public boolean saveRequest(CourseRequest courseRequest) {
        return DB.add(courseRequest);
    }

    // @Override
    public boolean editRequestByRequestId(UUID courseRequestId, CourseRequest newCourseRequest) {
        if (!courseRequestExists(courseRequestId)) {
            return false;
        }

        CourseRequest oldCourseRequest  = DB.stream()
                                            .filter(request -> request.getRequestId() == courseRequestId)
                                            .findFirst()
                                            .orElse(null); // We know the request exists at this point in code, so null will never be returned

        DB.set(DB.indexOf(oldCourseRequest), newCourseRequest);
        return true;
    }

    // @Override
    public boolean deleteRequestByRequestId(UUID courseRequestId) {
        if (!courseRequestExists(courseRequestId)) {
            return false;
        }

        Iterator<CourseRequest> deleteRequestIterator = DB.iterator();
        while (deleteRequestIterator.hasNext()) {
            if (deleteRequestIterator.next().getRequestId() != courseRequestId) continue;
            deleteRequestIterator.remove();
            return true;
        }

        return false;
    }

    // @Override
    public List<CourseRequest> getCourseRequestsByStudentId(Long studentId) {
        return DB.stream().filter(request -> request.getStudentId().equals(studentId)).collect(Collectors.toList());
    }

    // @Override
    public List<CourseRequest> getAllCourseRequests() {
        return DB;
    }

    // @Override
    public boolean courseRequestExists(UUID courseRequestId) {
        return DB.stream().filter(request -> request.getRequestId() == courseRequestId).findFirst().isPresent();
    }
}
