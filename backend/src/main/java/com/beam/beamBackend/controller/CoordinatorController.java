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
import com.beam.beamBackend.exception.ExceptionLogger;
import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.request.CoordinatorWishlistApproval;
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

/**
 * A Spring Controller class to handle retrieval, approval, and rejection
 * process
 * of coordinators
 * 
 * @version 1.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/coordinator")
public class CoordinatorController {
    private final CoordinatorWishlistService coordinatorWishlistService;

    /**
     * This method retrives submitted wishlists for the given coordinator
     * 
     * @param coordinatorUserId user id of the coordinator whom submtted
     *                          preApprovals
     *                          are retrieved
     * @return list of wishlists
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{coordinatorUserId}/wishlist")
    public ResponseEntity<Object> getCoordinatorsWishlists(
            @Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId) {
        try {
            ArrayList<Wishlist> wishlists = coordinatorWishlistService.getCoordinatorsWishlists(coordinatorUserId);
            return Response.create("Wishlists fetched!", HttpStatus.OK, wishlists);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);
        }
    }

    /**
     * This method retrieves the wishlist of the student if the student's
     * coordinator is the given coordinator.
     * 
     * @param coordinatorUserId user id of the coordinator whom submtted
     * preApprovals are retrieved
     * 
     * @return wishlist of the student
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{coordinatorUserId}/wishlist/student/{studentBilkentId}")
    public ResponseEntity<Object> getWishlist(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId,
            @Valid @PathVariable("studentBilkentId") Long studentBilkentId) {
        try {
            Optional<Wishlist> wishedCourses = coordinatorWishlistService.getWishlist(coordinatorUserId,
                    studentBilkentId);
            return Response.create("Wishlist of the student" + studentBilkentId + "is fetched", HttpStatus.OK,
                    wishedCourses);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);
        }
    }

    /**
     * This method determines the wishlist of the student if the student's
     * coordinator is the given coordinator.
     * 
     * @param coordinatorUserId user id of the coordinator whom submtted
     * preApprovals are retrieved
     * 
     * @return if the determining process occures successfully or not
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(path = "/wishlist/determineStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> determineWishlistStatus(
            @Valid @RequestBody CoordinatorWishlistApproval wishlistResult) {
        try {
            Wishlist wishlist = coordinatorWishlistService.determineWishlistStatus(wishlistResult);
            return Response.create("Wishlist status of students is changed!", HttpStatus.OK, wishlist);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);
        }
    }
}
