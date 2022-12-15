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
        verifyStudentHasWishlist(studentId)
        
        Wishlist wishlistOfStudent = wishlistRepository.findByStudentId(studentId).get();

        // Checks if student ID has a wishlist in the system
        if (!wishlistOfStudent.isPresent()) { // same as verifyStudentHasWishlist(studentId)
            throw new Exception("student with id " + studentId + " does not have a wishlist in the system");
        }

        return wishlistOfStudent.get();
    }

    @Override
    public boolean submitWishlist(Long studentId) throws Exception {
        // Checks if student ID has a wishlist in the system
        verifyStudentHasWishlist(studentId);

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

    /**
     * Verifies that a given student ID has a wishlist
     * saved in the repository. Throws an exception otherwise.
     * @param studentId bilkent ID of student
     * @return true if the student has a wishlist in the system, throws exception otherwise
     * @throws Exception thrown when the student does not have a wishlist in the system
     */
    private boolean verifyStudentHasWishlist(Long studentId) throws Exception {
        if (!wishlistRepository.existsByStudentId(studentId)) {
            throw new Exception("student with id " + studentId + " does not have a wishlist in the system");
        }
        
        return true;
    }
}
