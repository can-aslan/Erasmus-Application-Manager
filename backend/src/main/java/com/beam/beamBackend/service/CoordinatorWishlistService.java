package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.enums.CourseWishlistStatus;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.repository.IStaffRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IWishlistRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoordinatorWishlistService implements ICoordinatorWishlistService {
    private final IWishlistRepository wishlistRepository;
    private final IStaffRepository staffRepository;
    private final IStudentRepository studentRepository;

    @Override
    public ArrayList<Wishlist> getCoordinatorsWishlists(@Valid UUID coordinatorId) throws Exception {
        try {
            List<Wishlist> wishlists = wishlistRepository.findAll();
            ArrayList<Wishlist> coordinatorsWishLists = new ArrayList<Wishlist>();

            Optional<Staff> coordinator = staffRepository.findByUserId(coordinatorId);

            // Check is the coordinator exists
            if (!coordinator.isPresent()){
                throw new Exception("Coordinator is not found!");
            }
           
            for (int i = 0; i < wishlists.size(); i++) {
                Optional<Student> aStudent = studentRepository.findByUserBilkentId(wishlists.get(i).getStudentId());
                if (!aStudent.isPresent()) {
                    throw new Exception("The student is not found!");
                }

                if (aStudent.get().getCoordinator().getUser().getId() == coordinator.get().getUser().getId()) {
                    coordinatorsWishLists.add(wishlists.get(i));
                }
            }

            return coordinatorsWishLists;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Optional<Wishlist> getWishlist(@Valid UUID coordinatorId, @Valid Long studentId) throws Exception {
        try {
            Optional<Student> aStudent = studentRepository.findByUserBilkentId(studentId);

            // Check is the student exists
            if (!aStudent.isPresent()) {
                throw new Exception("Student is not found!");
            }

            Optional<Staff> coordinator = staffRepository.findById(coordinatorId);

            // Check is the coordinator exists
            if (!coordinator.isPresent()) {
                throw new Exception("Coordinator is not found!");
            }

            if (aStudent.get().getCoordinator().getId() == coordinator.get().getId()) {
                throw new Exception("You are not coordinator of this student!");
            } 

            Optional<Wishlist> wishlist = wishlistRepository.findByStudentId(studentId);
            if (!wishlist.isPresent()) {
                throw new Exception("The student does not have a wishlist!");
            }

            return wishlist;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void determineWishlistStatus(@Valid UUID coordinatorId, @Valid Long studentId, @Valid CourseWishlistStatus status) throws Exception {
        try {
            Optional<Staff> coordinator = staffRepository.findById(coordinatorId);

            if (!coordinator.isPresent()) {
                throw new Exception("Coordinator is not found!");
            }

            Optional<Student> aStudent = studentRepository.findByUserBilkentId(studentId);
            if (!aStudent.isPresent()) {
                throw new Exception("Student is not found!");
            }

            Optional<Wishlist> wishlist = wishlistRepository.findByStudentId(studentId);
            if (!wishlist.isPresent()) {
                throw new Exception("Student does not have a course wishlist!");
            }
            
            wishlist.get().setStatus(status);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
