package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;

import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.model.WishlistItem;

public interface IWishlistService {
    List<Wishlist> getAllWishlists() throws Exception;
    Wishlist getWishlistByStudentId(Long studentId) throws Exception;

    boolean submitWishlist(Wishlist wishlist) throws Exception;
    boolean submitWishlist(Long studentId) throws Exception;

    boolean addWishlistItem(Long studentId, WishlistItem itemToAdd) throws Exception;

    boolean removeWishlistItem(Long studentId, WishlistItem itemToRemove) throws Exception;
    boolean removeWishlistItem(Long studentId, UUID wishlistItemUUID) throws Exception;
    
    List<WishlistItem> getAllWishlistItems() throws Exception;
    List<WishlistItem> getAllWishlistItemsOfStudent(Long studentId) throws Exception;
}
