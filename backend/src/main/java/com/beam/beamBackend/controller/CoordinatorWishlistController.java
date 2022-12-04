package com.beam.beamBackend.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.CourseWishlist;
import com.beam.beamBackend.service.CoordinatorWishlistService;

@RestController
@RequestMapping(path = "api/coordinator/wishlist")
public class CoordinatorWishlistController {
    private final CoordinatorWishlistService coordinatoWishlistService;

    @Autowired
    public CoordinatorWishlistController( CoordinatorWishlistService coordinatoWishlistService){
        this.coordinatoWishlistService = coordinatoWishlistService;
    }

    public ArrayList<CourseWishlist> getWaitingWishlist(){
        return coordinatoWishlistService.getWaitingWishlist();
    }

    public ArrayList<Course> viewWishList(long studentId){
        return coordinatoWishlistService.viewWishList(studentId);
    }
}
