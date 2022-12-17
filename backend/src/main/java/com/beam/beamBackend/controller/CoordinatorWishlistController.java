package com.beam.beamBackend.controller;

import com.beam.beamBackend.service.CoordinatorWishlistService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.beam.beamBackend.enums.CourseWishlistStatus;
import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.response.Response;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
@RequestMapping("api/v1/coordinator")
public class CoordinatorWishlistController {
    private final CoordinatorWishlistService coordinatorWishlistService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{coordinatorUserId}")
    public ResponseEntity<Object> getCoordinatorsWishlists(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId){
        try{
            ArrayList<Wishlist> wishlists = coordinatorWishlistService.getCoordinatorsWishlists(coordinatorUserId);
            return Response.create("Wishlists fetched!", HttpStatus.OK, wishlists);
        }catch(Exception e){
            return Response.create( "Wishlist couldn't fetched", 499 );
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{coordinatorUserId}/student/{studentBilkentId}")
    public ResponseEntity<Object> getWishlist(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId, @Valid @PathVariable("studentBilkentId") Long studentBilkentId){
        try{
            Optional<Wishlist> wishedCourses = coordinatorWishlistService.getWishlist(coordinatorUserId, studentBilkentId);
            return Response.create("Wishlist of the student" + studentBilkentId + "is fetced", HttpStatus.OK, wishedCourses);
        } catch (Exception e){
            return Response.create("Wishlist of the student" + studentBilkentId + "couldn't fetced", 499);
        }
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping( path = "/{coordinatorUserId}/student/{studentBilkentId}/status/{status}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> determineWishlistStatus(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId, @Valid @PathVariable("studentBilkentId") Long studentBilkentId, @Valid @PathVariable("status") CourseWishlistStatus status){
        try{
            coordinatorWishlistService.determineWishlistStatus(coordinatorUserId, studentBilkentId, status);
            return Response.create("Wishlist status of students is changed!", HttpStatus.OK);
        }catch(Exception e){
            return Response.create("Wishlist status of students couldn't changed!", 499);
        }
    }


    /*@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping( "/{syllabusUrl}")
    public ResponseEntity<Object> getCourseSyllabusUrl(@Valid @PathVariable("coordinatorId") UUID coordinatorId, @Valid @PathVariable("studentId") Long studentId){
        try{
            String syllabusUrl = coordinatorWishlistService.getCourseSyllabusUrl(coordinatorId, studentId);
            return Response.create("Syllabus url is fetched", HttpStatus.OK, syllabusUrl);
        }catch (Exception e){
            return Response.create("Syllabus url couldn't fetced!", 499);
        }   
    }*/
}

