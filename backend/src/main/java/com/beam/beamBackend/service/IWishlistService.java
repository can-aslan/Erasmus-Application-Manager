package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.model.WishlistItem;
import com.beam.beamBackend.model.WishlistItemMapping;

public interface IWishlistService {
    List<Wishlist> getAllWishlists() throws Exception;
    List<Wishlist> getAllWishlistsOfCoordinator(UUID coordinatorId) throws Exception;

    Wishlist getWishlistByStudentId(Long studentId) throws Exception;

    // boolean submitWishlist(Wishlist wishlist) throws Exception;
    boolean submitWishlist(Long studentId) throws Exception;
    boolean addWishlistItem(Long studentId, WishlistItem itemToAdd) throws Exception;
    boolean removeWishlistItem(Long studentId, WishlistItem itemToRemove) throws Exception;
    boolean removeWishlistItem(Long studentId, UUID wishlistItemUUID) throws Exception;
    boolean createEmptyWishlistIfNew(Long studentId) throws Exception;
    boolean addWishlistItemMapping(Long studentId, String bilkentCourse, WishlistItemMapping itemMappingToAdd) throws Exception;

    List<WishlistItem> getAllWishlistItems() throws Exception;
    List<WishlistItem> getAllWishlistItemsOfStudent(Long studentId) throws Exception;
    List<WishlistItemMapping> getAllWishlistMappingOfItem(UUID itemId) throws Exception;
    UUID getWishlistItemUUID(Long studentId, String bilkentCourse) throws Exception;
}
