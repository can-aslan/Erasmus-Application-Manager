package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.model.WishlistItem;
import com.beam.beamBackend.repository.IWishlistItemMappingRepository;
import com.beam.beamBackend.repository.IWishlistItemRepository;
import com.beam.beamBackend.repository.IWishlistRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistService implements IWishlistService {

    private final IWishlistRepository wishlistRepository;
    private final IWishlistItemRepository itemRepository;
    private final IWishlistItemMappingRepository mappingRepository;

    @Override
    public List<Wishlist> getAllWishlists() throws Exception {
        return wishlistRepository.findAll();
    }

    @Override
    public Wishlist getWishlistByStudentId(Long studentId) throws Exception {
        Optional<Wishlist> wishlistOfStudent = wishlistRepository.findByStudentId(studentId);

        // Checks if student ID has a wishlist in the system
        if (!wishlistOfStudent.isPresent()) {
            throw new Exception("student with id " + studentId + " does not have a wishlist in the system");
        }

        return wishlistOfStudent.get();
    }

    @Override
    public boolean submitWishlist(Long studentId) throws Exception {
        // Checks if student ID has a wishlist in the system
        if (!wishlistRepository.existsByStudentId(studentId)) {
            throw new Exception("student with id " + studentId + " does not have a wishlist in the system");
        }

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
 
    private boolean studentHasWishlist(Long studentId) {
        return wishlistRepository.existsByStudentId(studentId);
    }
}
