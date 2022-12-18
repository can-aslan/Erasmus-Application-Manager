package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.CourseWishlistStatus;
import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.repository.IStaffRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IWishlistRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CoordinatorWishlistService {
    private final IWishlistRepository wishlistRepository;
    private final IStaffRepository staffRepository;
    private final IStudentRepository studentRepository;

    public ArrayList<Wishlist> getCoordinatorsWishlists(@Valid UUID coordinatorId) throws Exception {
        try {
            List<Wishlist> wishlists = wishlistRepository.findAll();

            System.out.println(wishlists.get(0));
            ArrayList<Wishlist> coordinatorsWishLists = new ArrayList<>();

            Optional<Staff> coordinator = staffRepository.findByUserId(coordinatorId);

            // Check if the coordinator exists
            if (!coordinator.isPresent()) {
                throw new Exception("Coordinator is not found!");
            } else {
                for (int i = 0; i < wishlists.size(); i++) {
                    Optional<Student> aStudent = studentRepository.findByUserBilkentId(wishlists.get(i).getStudentId());

                    if (!aStudent.isPresent()) {                        
                        throw new Exception("The student is not found!");
                    }

                    if (aStudent.get().getCoordinator().getUser().getId() == coordinator.get().getUser().getId()){
                        coordinatorsWishLists.add(wishlists.get(i));
                    }
                }
            }

            return coordinatorsWishLists;

        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Optional<Wishlist> getWishlist(@Valid UUID coordinatorId, @Valid Long studentId) throws Exception {

        try{
            Optional<Student> aStudent = studentRepository.findByUserBilkentId(studentId);
            // Check is the student exists
            if (!aStudent.isPresent()){
                throw new Exception("Student is not found!");
            }

            Optional<Staff> coordinator = staffRepository.findByUserId(coordinatorId);
            // Check is the coordinator exists
            if (!coordinator.isPresent()){
                throw new Exception("Coordinator is not found!");
            }

            if(aStudent.get().getCoordinator().getId() == coordinator.get().getUser().getId()){
                throw new Exception("You are not coordinator of this student!");
            } else {
                Optional<Wishlist> wishlist = wishlistRepository.findByStudentId(studentId);
                if (!wishlist.isPresent()){
                    throw new Exception("The student does not have a wishlist!");
                } else {
                    return wishlist;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }

    }

    public void determineWishlistStatus(@Valid UUID coordinatorId, @Valid Long studentId, @Valid CourseWishlistStatus status) throws Exception {
        try {
            Optional<Staff> coordinator = staffRepository.findByUserId(coordinatorId);
            if (!coordinator.isPresent()){
                throw new Exception("Coordinator is not found!");
            }
            Optional<Student> aStudent = studentRepository.findByUserBilkentId(studentId);
            if (!aStudent.isPresent()){
                throw new Exception("Student is not found!");
            }

            Optional<Wishlist> wishlist = wishlistRepository.findByStudentId(studentId);
            if (!wishlist.isPresent()){
                throw new Exception("Student does not have a course wishlist!");
            }
            else {
                wishlist.get().setStatus(status);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
}
