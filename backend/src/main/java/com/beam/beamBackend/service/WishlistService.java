package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.CourseWishlistStatus;
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
        // Checks if student ID has a wishlist in the system
        verifyStudentHasWishlist(studentId);
        
        Optional<Wishlist> wishlistOfStudent = wishlistRepository.findByStudentId(studentId);

        if (!wishlistOfStudent.isPresent()) { // same as verifyStudentHasWishlist(studentId), but a redundant check just in case
            throw new Exception("student with id " + studentId + " could not retrieve wishlist");
        }

        return wishlistOfStudent.get();
    }

    @Override
    public boolean submitWishlist(Long studentId) throws Exception {
        // Checks if student ID has a wishlist in the system
        verifyStudentHasWishlist(studentId);

        return wishlistRepository.updateWishlistItemStatus(studentId, CourseWishlistStatus.PENDING) > 0; // > 0 means at least one row is updated
    }

    @Override
    public boolean addWishlistItem(Long studentId, WishlistItem itemToAdd) throws Exception {
        // Checks if student ID has a wishlist in the system
        verifyStudentHasWishlist(studentId);
        
        // Maybe later, check if bilkent course already exists in wishlist item

        // Wishlist studentWishlist = getWishlistByStudentId(studentId); // also checks if the student has a wishlist in the system, if we get no errors we know we have a valid wishlist object

        if (itemToAdd.getStudentId() != studentId) {
            throw new Exception("student with id " + studentId + " cannot add wishlist item, student ids do not match");
        }

        itemRepository.save(itemToAdd);
        return true;
    }

    @Override // TODO
    public boolean removeWishlistItem(Long studentId, WishlistItem itemToRemove) throws Exception {
        // Checks if student ID has a wishlist in the system
        verifyStudentHasWishlist(studentId);

        // Wishlist studentWishlist = getWishlistByStudentId(studentId); // also checks if the student has a wishlist in the system, if we get no errors we know we have a valid wishlist object

        if (itemToRemove.getStudentId() != studentId) {
            throw new Exception("student with id " + studentId + " cannot remove wishlist item, student ids do not match");
        }

        // ASSUMPTION: onlt 1 bilkent course exists for 1 student id
        // add findbycourseandstudentid to repository 
        itemRepository.deleteByStudentIdAndBilkentCourse(studentId, itemToRemove.getBilkentCourse());
        return true;
    }

    @Override
    public boolean removeWishlistItem(Long studentId, UUID wishlistItemUUID) throws Exception {
        // Checks if student ID has a wishlist in the system
        verifyStudentHasWishlist(studentId);

        if (!itemRepository.existsById(wishlistItemUUID)) {
            throw new Exception("student with id " + studentId + " cannot remove wishlist item, wishlist item with UUID " + wishlistItemUUID + " does not exist");
        }

        itemRepository.deleteById(wishlistItemUUID);
        return true;
    }

    @Override
    public List<WishlistItem> getAllWishlistItems() throws Exception {
        return itemRepository.findAll();
    }

    @Override
    public List<WishlistItem> getAllWishlistItemsOfStudent(Long studentId) throws Exception {
        return itemRepository.findAllByStudentId(studentId);
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
