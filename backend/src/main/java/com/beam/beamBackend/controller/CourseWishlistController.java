package com.beam.beamBackend.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.model.WishlistItem;
import com.beam.beamBackend.model.WishlistItemMapping;
import com.beam.beamBackend.request.WishlistItemRequest;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IWishlistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/wishlist")
public class CourseWishlistController {
    
    private final IWishlistService courseWishlistService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping(path = "/fetch")
    public ResponseEntity<Object> getAllWishlists()
    {
        try {
            List<Wishlist> responseResult = courseWishlistService.getAllWishlists();
            
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

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping(path = "/fetch/coordinator/{coordinatorId}")
    public ResponseEntity<Object> getAllWishlistsOfCoordinator(@PathVariable("coordinatorId") UUID coordinatorId)
    {
        try {
            List<Wishlist> responseResult = courseWishlistService.getAllWishlistsOfCoordinator(coordinatorId);
            
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

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping(path = "/fetch/{studentId}")
    public ResponseEntity<Object> getWishlistOfStudent(@PathVariable("studentId") Long studentId)
    {
        try {
            // Check if the student has a wishlist, if not open a new wishlist
            courseWishlistService.createEmptyWishlistIfNew(studentId);
            
            Wishlist responseResult = courseWishlistService.getWishlistByStudentId(studentId);
            
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

    /*
    @PostMapping(path = "/submit")
    public ResponseEntity<Object> submitWishlist(@Valid @RequestBody Wishlist courseWishlist)
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
    */

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
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

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(path = "/add/{studentId}")
    public ResponseEntity<Object> addWishlistItem(@PathVariable("studentId") Long studentId, @Valid @RequestBody WishlistItemRequest wishlistItemRequest)
    {
        try {
            // Check if the student has a wishlist, if not open a new wishlist
            courseWishlistService.createEmptyWishlistIfNew(studentId);
            
            // Convert WishlistItemRequest to WishlistItem
            WishlistItem item = WishlistItem.toWishlistItem(wishlistItemRequest, UUID.randomUUID(), studentId, courseWishlistService.getWishlistByStudentId(studentId));

            // Execute addWishlistItem operation
            boolean responseResult = courseWishlistService.addWishlistItem(studentId, item);
            
            UUID addedWishlistItemUUID = null;

            // If the wishlist item is added successfully
            if (responseResult) {
                // Get the added wishlist item's UUID
                addedWishlistItemUUID = courseWishlistService.getWishlistItemUUID(studentId, wishlistItemRequest.getBilkentCourse());
            
                // Save Wishlist Item Mappings
                for (WishlistItemMapping wim : wishlistItemRequest.getMappings()) {
                    courseWishlistService.addWishlistItemMapping(studentId, item.getBilkentCourse(), wim);
                }
            }

            return 
            responseResult ?
                Response.create("adding a course wishlist item successful", HttpStatus.OK, addedWishlistItemUUID)
                :
                Response.create("adding a course wishlist item unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("adding a course wishlist item failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
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
