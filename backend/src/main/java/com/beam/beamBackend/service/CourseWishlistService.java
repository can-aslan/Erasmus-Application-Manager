package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.beam.beamBackend.enums.CourseWishlistStatus;
import com.beam.beamBackend.model.CourseWishlist;
import com.beam.beamBackend.model.CourseWishlistItem;
import com.beam.beamBackend.repository.ICourseWishlistItemRepository;
import com.beam.beamBackend.repository.ICourseWishlistRepository;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Deprecated
public class CourseWishlistService implements ICourseWishlistService {

    private final ICourseWishlistRepository wishlistRepository;
    private final ICourseWishlistItemRepository wishlistItemRepository;

    @Override
    public List<CourseWishlist> getAllWishlists() throws Exception {
        return wishlistRepository.findAll();
    }

    @Override
    public boolean submitWishlist(CourseWishlist wishlist) throws Exception {
        Long studentId = wishlist.getStudentId();

        // Checks if the student already has a wishlist in the system
        if (wishlistRepository.existsByStudentId(studentId)) {
            throw new Exception("student with id " + studentId + " already has a wishlist in the system");
        }

        // Checks if the provided UUIDs of the wishlist items are valid and present in the system
        List<UUID> wishlistItems = wishlist.getWishlistItems();
        for (UUID id : wishlistItems) {
            if (!wishlistItemRepository.existsById(id)) {
                throw new Exception("course wishlist for student with id " + studentId + " has wrong course wishlist item UUID(s)");
            }
        }

        // At this point we know the course wishlist items exists in the system,
        // and that the student has no course wishlist(s) in the system.
        wishlist.setStatus(CourseWishlistStatus.PENDING);
        wishlistRepository.save(wishlist);
        return true;
    }

    @Override
    public boolean submitWishlist(Long studentId) throws Exception {
        // Checks whether or not if the student has a wishlist in the system
        if (!wishlistRepository.existsByStudentId(studentId)) {
            throw new Exception("student with id " + studentId + " does not have a wishlist in the system");
        }

        // Checks if the provided UUIDs of the wishlist items are valid and present in the system
        CourseWishlist wishlist = wishlistRepository.findCourseWishlistByStudentId(studentId); 
        List<UUID> wishlistItems = wishlist.getWishlistItems();
        for (UUID id : wishlistItems) {
            if (!wishlistItemRepository.existsById(id)) {
                throw new Exception("course wishlist for student with id " + studentId + " has wrong course wishlist item UUID(s)");
            }
        }

        // At this point we know the course wishlist items exists in the system,
        // and that the student has no course wishlist(s) in the system.

        // Delete old wishlist from the repository
        wishlistRepository.deleteById(wishlist.getWishlistId());

        // Edit wishlist and save new wishlist to repository
        wishlist.setStatus(CourseWishlistStatus.PENDING);
        wishlistRepository.save(wishlist);
        return true;
    }

    @Override
    public List<CourseWishlist> getAllWishlistsOfStudent(Long studentId) throws Exception {
        return wishlistRepository.findAllByStudentId(studentId);
    }

    @Override
    public boolean addWishlistItem(Long studentId, CourseWishlistItem itemToAdd) throws Exception {
        // Checks whether or not if the student has a wishlist in the system
        if (!wishlistRepository.existsByStudentId(studentId)) {              
            // Create new wishlist
            // Create new List<UUID> to be added to the new wishlist
            List<UUID> wishlistItems = new ArrayList<UUID>();
            wishlistItems.add(itemToAdd.getWishlistItemId());

            wishlistRepository.save(new CourseWishlist(
                UUID.randomUUID(),
                wishlistItems,
                studentId,
                CourseWishlistStatus.WAITING));

            // Add the wishlist item to wishlist item repository
            wishlistItemRepository.save(itemToAdd);
            return true;
        }

        // At this point we know the user has a wishlist in the system
        CourseWishlist wishlist = wishlistRepository.findCourseWishlistByStudentId(studentId); 

        // Checks if the wishlist is sent
        if (wishlist.getStatus() != CourseWishlistStatus.WAITING ) {
            throw new Exception("course wishlist for student with id " + studentId + " is already sent or approved, cannot be edited");
        }

        // Add wishlist item to wishlist
        List<UUID> newWishlistItems = wishlist.getWishlistItems();
        newWishlistItems.add(itemToAdd.getWishlistItemId());
        wishlist.setWishlistItems(newWishlistItems); 
        
        // Save changed wishlist in the repository
        wishlistRepository.deleteById(wishlist.getWishlistId());
        wishlistRepository.save(wishlist);

        // Add the wishlist item to wishlist item repository
        wishlistItemRepository.save(itemToAdd);
        return true;
    }

    @Override
    public boolean removeWishlistItem(Long studentId, CourseWishlistItem itemToRemove) throws Exception {
        // Checks whether or not if the student has a wishlist item in the system
        if (!wishlistItemRepository.existsByStudentId(studentId)) {
            throw new Exception("student with id " + studentId + " cannot delete course wishlist item, does not have any");
        }

        // Checks whether or not if the student has a wishlist in the system
        if (!wishlistRepository.existsByStudentId(studentId)) {
            throw new Exception("student with id " + studentId + " cannot delete course wishlist item, student does not have a wishlist in the system");
        }

        // At this point we know the user has a wishlist in the system
        CourseWishlist wishlist = wishlistRepository.findCourseWishlistByStudentId(studentId); 
        List<UUID> wishlistItemsUUIDs = wishlist.getWishlistItems();

        // At this point we know the user has at least one wishlist item in the system (maybe a different wishlist item)
        List<CourseWishlistItem> wishlistItems = wishlistItemRepository.findAllByStudentId(studentId); 

        // Check if the wishlist item to delete exists in the system
        for (CourseWishlistItem item : wishlistItems) {
            // If the itemToRemove is found
            if (item.equalsWithoutUUID(itemToRemove)) {
                // Remove item from wishlist item repository
                wishlistItemRepository.deleteById(item.getWishlistItemId());

                // Remove old wishlist from repository
                wishlistRepository.deleteById(wishlist.getWishlistId());

                // Remove item from wishlist
                wishlistItemsUUIDs.remove(item.getWishlistItemId());
                wishlist.setWishlistItems(wishlistItemsUUIDs);

                // Save edited wishlist to repository
                wishlistRepository.save(wishlist);
                return true;
            }
        }
        
        throw new Exception("student with id " + studentId + " cannot delete course wishlist item, could not find item to remove");
    }

    @Override
    public boolean removeWishlistItem(Long studentId, UUID wishlistItemUUID) throws Exception {
        // Checks whether or not if the given wishlist item UUID exists in the system
        if (!wishlistItemRepository.existsById(wishlistItemUUID)) {
            throw new Exception("student with id " + studentId + " cannot delete course wishlist item, specified item does not exist");
        }

        // Checks whether or not if the student owns the wishlist item in the system.
        // We can also use get() because at this point we know
        // the Optional<> does not contain a null value due to a previous check
        CourseWishlistItem item = wishlistItemRepository.findById(wishlistItemUUID).get();
        Long itemStudentId = item.getStudentId();
        if (!itemStudentId.equals(studentId)) {
            throw new Exception("student with id " + studentId + " cannot delete course wishlist item, does not own it");
        }

        // At this point we know the student has the specified wishlist item in the repository
        wishlistItemRepository.deleteById(wishlistItemUUID);
        return true;
    }

    @Override
    public List<CourseWishlistItem> getAllWishlistItems() throws Exception {
        return wishlistItemRepository.findAll();
    }

    @Override
    public List<CourseWishlistItem> getAllWishlistItemsOfStudent(Long studentId) throws Exception {
        return wishlistItemRepository.findAllByStudentId(studentId);
    }
}
