package com.beam.beamBackend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.model.CourseWishlist;
import com.beam.beamBackend.model.CourseWishlistItem;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.ICourseWishlistService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/wishlist")
public class CourseWishlistController {
    
    private final ICourseWishlistService courseWishlistService;

    @GetMapping(path = "/fetch")
    public ResponseEntity<Object> getAllWishlists()
    {
        try {
            List<CourseWishlist> responseResult = courseWishlistService.getAllWishlists();
            
            return 
            responseResult != null ?
                Response.create("course wishlists fetch successful", HttpStatus.OK, responseResult)
                :
                Response.create("course wishlists fetch unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("course wishlists fetch failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/fetch/{studentId}")
    public ResponseEntity<Object> getAllWishlistsOfStudent(@PathVariable("studentId") Long studentId)
    {
        try {
            List<CourseWishlist> responseResult = courseWishlistService.getAllWishlistsOfStudent(studentId);
            
            return 
            responseResult != null ?
                Response.create("course wishlists fetch successful", HttpStatus.OK, responseResult)
                :
                Response.create("course wishlists fetch unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("course wishlists fetch failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/submit")
    public ResponseEntity<Object> submitWishlist(@Valid @RequestBody CourseWishlist courseWishlist)
    {
        try {
            boolean responseResult = courseWishlistService.submitWishlist(courseWishlist);
            
            return 
            responseResult ?
                Response.create("course wishlist submission successful", HttpStatus.OK, responseResult)
                :
                Response.create("course wishlist submission unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("course wishlist submission failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/submit/{studentId}")
    public ResponseEntity<Object> submitWishlist(@PathVariable("studentId") Long studentId)
    {
        try {
            boolean responseResult = courseWishlistService.submitWishlist(studentId);
            
            return 
            responseResult ?
                Response.create("course wishlist submission successful", HttpStatus.OK, responseResult)
                :
                Response.create("course wishlist submission unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("course wishlist submission failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/add/{studentId}")
    public ResponseEntity<Object> addWishlistItem(@PathVariable("studentId") Long studentId, @Valid @RequestBody CourseWishlistItem courseWishlistItem)
    {
        try {
            boolean responseResult = courseWishlistService.addWishlistItem(studentId, courseWishlistItem);
            
            return 
            responseResult ?
                Response.create("adding a course wishlist item successful", HttpStatus.OK, responseResult)
                :
                Response.create("adding a course wishlist item unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("adding a course wishlist item failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/delete/{studentId}/{wishlistItemUUID}")
    public ResponseEntity<Object> deleteWishlistItem(@PathVariable("studentId") Long studentId, @PathVariable("wishlistItemUUID") UUID wishlistItemUUID)
    {
        try {
            boolean responseResult = courseWishlistService.removeWishlistItem(studentId, wishlistItemUUID);
            
            return 
            responseResult ?
                Response.create("removing a course wishlist item successful", HttpStatus.OK, responseResult)
                :
                Response.create("removing a course wishlist item unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("removing a course wishlist item failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
