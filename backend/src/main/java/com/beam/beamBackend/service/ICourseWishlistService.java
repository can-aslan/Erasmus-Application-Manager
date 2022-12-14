package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;

import com.beam.beamBackend.model.CourseWishlist;
import com.beam.beamBackend.model.CourseWishlistItem;

public interface ICourseWishlistService {
    List<CourseWishlist> getAllWishlists() throws Exception;
    boolean submitWishlist(CourseWishlist wishlist) throws Exception;
    boolean submitWishlist(Long studentId) throws Exception;
    List<CourseWishlist> getAllWishlistsOfStudent(Long studentId) throws Exception;
    boolean addWishlistItem(Long studentId, CourseWishlistItem itemToAdd) throws Exception;
    boolean removeWishlistItem(Long studentId, CourseWishlistItem itemToRemove) throws Exception;
    boolean removeWishlistItem(Long studentId, UUID wishlistItemUUID) throws Exception;
    List<CourseWishlistItem> getAllWishlistItems() throws Exception;
    List<CourseWishlistItem> getAllWishlistItemsOfStudent(Long studentId) throws Exception;
}
