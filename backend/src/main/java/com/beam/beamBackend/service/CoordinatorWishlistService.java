// package com.beam.beamBackend.service;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.beam.beamBackend.model.Course;
// import com.beam.beamBackend.model.CourseWishlist;
// import com.beam.beamBackend.model.Staff;
// import com.beam.beamBackend.model.Student;
// import com.beam.beamBackend.model.User;
// import com.beam.beamBackend.repository.ICourseWishlistRepository;
// import com.beam.beamBackend.repository.IStaffRepository;
// import com.beam.beamBackend.repository.IStudentRepository;

// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;


// @RequiredArgsConstructor
// @Service
// public class CoordinatorWishlistService {
//     private final ICourseWishlistRepository courseWishlistRepository;
//     private final IStaffRepository staffRepository;
//     private final IStudentRepository studentRepository;

//     public ArrayList<CourseWishlist> getWaitingWishlist(@Valid UUID coordinatorBilkentId) throws Exception {
//         try {
//             List<CourseWishlist> courseWishlists = courseWishlistRepository.findByStatusPending();
//             ArrayList<CourseWishlist> waitingWishlists = new ArrayList<>();

//             Optional<Staff> coordinator = staffRepository.findById(coordinatorBilkentId);
//             // Check is the coordinator exists
//             if (!coordinator.isPresent()){
//                 throw new Exception("Coordinator is not found!");
//             }
//             else {
//                 for (int i = 0; i < courseWishlists.size(); i++){
//                     Optional<Student> aStudent = studentRepository.findByUserBilkentId(courseWishlists.get(i).getStudentId());
//                     if (aStudent.get().getCoordinator().getId() == coordinator.get().getId()){
//                         waitingWishlists.add(courseWishlists.get(i));
//                     }
//                 }
//             }

//             return waitingWishlists;

//         }catch(Exception e){
//             e.printStackTrace();
//             throw e;
//         }

//     }

//     public Optional<CourseWishlist> getWishlist(@Valid UUID coordinatorBilkentId, @Valid Long studentId) throws Exception {

//         try{
//             Optional<Student> aStudent = studentRepository.findByUserBilkentId(studentId);
//             // Check is the student exists
//             if (!aStudent.isPresent()){
//                 throw new Exception("Coordinator is not found!");
//             }

//             Optional<Staff> coordinator = staffRepository.findById(coordinatorBilkentId);
//             // Check is the coordinator exists
//             if (!coordinator.isPresent()){
//                 throw new Exception("Coordinator is not found!");
//             }

//             if(aStudent.get().getCoordinator().getId() == coordinator.get().getId()){
//                 throw new Exception("You are not coordinator of this student!");
//             } else {
//                 Optional<CourseWishlist> courseWishlist = courseWishlistRepository.findCourseWishlistByStudentId(studentId);
//                 if (!courseWishlist.isPresent()){
//                     throw new Exception("The student does not have a wishlist!");
//                 } else {
//                     return courseWishlist;
//                 }
//             }

//         }catch(Exception e){
//             e.printStackTrace();
//             throw e;
//         }

//     }

//     public boolean determineWishlistStatus(@Valid UUID coordinatorBilkentId, @Valid Long studentId, @Valid boolean status) throws Exception {
//         try {
//             Optional<Staff> coordinator = staffRepository.findById(coordinatorBilkentId);
//             if (!coordinator.isPresent()){
//                 throw new Exception("Coordinator is not found!");
//             }
//             Optional<Student> aStudent = studentRepository.findByUserBilkentId(studentId);
//             if (!aStudent.isPresent()){
//                 throw new Exception("Student is not found!");
//             }

//             Optional<CourseWishlist> courseWishlist = courseWishlistRepository.findCourseWishlistByStudentId(studentId);
//             if (!courseWishlist.isPresent()){
//                 throw new Exception("Student does not have a course wishlist!");
//             }
//             else {
//                 UUID wishlistId = courseWishlist.get().getWishlistId();
//                 return courseWishlistRepository.updateWishlist(wishlistId, status);
//             }
//         }catch(Exception e){
//             e.printStackTrace();
//             throw e;
//         }
//     }
    
// }
