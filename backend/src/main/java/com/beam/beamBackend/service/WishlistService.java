package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;

import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.model.WishlistItem;

public class WishlistService implements IWishlistService {

    @Override
    public List<Wishlist> getAllWishlists() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Wishlist getWishlistByStudentId(Long studentId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean submitWishlist(Wishlist wishlist) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean submitWishlist(Long studentId) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addWishlistItem(Long studentId, WishlistItem itemToAdd) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeWishlistItem(Long studentId, WishlistItem itemToRemove) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeWishlistItem(Long studentId, UUID wishlistItemUUID) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<WishlistItem> getAllWishlistItems() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<WishlistItem> getAllWishlistItemsOfStudent(Long studentId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
}
