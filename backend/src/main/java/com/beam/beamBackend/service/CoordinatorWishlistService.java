package com.beam.beamBackend.service;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.CourseWishlist;
import com.beam.beamBackend.repository.CoordinatorWishlistRepository;

@Service
public class CoordinatorWishlistService {
    private final CoordinatorWishlistRepository coordinatorWishlistRepository;

    @Autowired
    public CoordinatorWishlistService(CoordinatorWishlistRepository coordinatorWishlistRepository){
        this.coordinatorWishlistRepository = coordinatorWishlistRepository;
    }

    public ArrayList<CourseWishlist> getWaitingWishlist() {
        return null;//coordinatorWishlistRepository.findWishlist();
    }

    public ArrayList<Course> viewWishList(long studentId) {
        return null;//coordinatorWishlistRepository.viewWishList(studentId);
    }
}
