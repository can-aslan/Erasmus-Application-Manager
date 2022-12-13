package com.beam.beamBackend.service;

import java.util.List;
import com.beam.beamBackend.model.CourseWishlist;
import com.beam.beamBackend.model.CourseWishlistItem;

public interface ICourseWishlistService {
    List<CourseWishlist> getAllWishlists();
    boolean submitWishlist(CourseWishlist wishlist);
    boolean submitWishlist(Long studentId);
    List<CourseWishlist> getAllWishlistsOfStudent(Long studentId);
    boolean addWishlistItem(Long studentId, CourseWishlistItem itemToAdd);
    boolean removeWishlistItem(Long studentId, CourseWishlistItem itemToRemove);
    List<CourseWishlistItem> getAllWishlistItems();
    List<CourseWishlistItem> getAllWishlistItemsOfStudent(Long studentId);
}
