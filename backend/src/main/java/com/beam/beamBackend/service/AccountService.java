package com.beam.beamBackend.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.RegisterStaff;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IStaffRepository;
import com.beam.beamBackend.request.ChangePassword;
import com.beam.beamBackend.request.InstructorCourseAdd;
import com.beam.beamBackend.request.StaffRequest;
import com.beam.beamBackend.response.RInstructorCourseAdd;
import com.beam.beamBackend.request.UserRequest;
import com.beam.beamBackend.response.RLoginUser;
import com.beam.beamBackend.response.RRefreshToken;
import com.beam.beamBackend.response.RRegisterStaff;
import com.beam.beamBackend.security.JWTFilter;
import com.beam.beamBackend.security.JWTUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {
    public static int hashStrength = 10;

    @Autowired
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    // @Autowired
    private final IAccountRepository accountRepository;
    private final IStaffRepository staffRepository;
    private final InstructorCourseService instructorCourseService;
    private final IJWTUserService jwtUserService;

    @Autowired
    private final JWTUtils jwtUtils;

    @Override
    public RLoginUser login(UserLogin user) throws Exception {
        try {
            User dbUser = accountRepository.findUserByBilkentId(Long.parseLong(user.getBilkentId()));

            if (dbUser == null) {
                throw new Exception("user is not found");
            }
            
            String hashedPassword = dbUser.getPassword();
            //String hashedPassword = accountRepository.getPasswordIfUserExist(Long.parseLong(user.getBilkentId()));
            boolean passwordMatch = bCryptPasswordEncoder.matches(user.getPassword(), hashedPassword);

            if (!passwordMatch) {
                throw new Exception("passwords do not match");
            }

            System.out.println("passwords are matched");

            // authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getBilkentId(), user.getPassword()));
            // User appUser = accountRepository.findUserByBilkentId(Long.parseLong(user.getBilkentId()));
            final UserDetails userDetails = jwtUserService.loadUserByUsername(user.getBilkentId());
            final String accessToken = jwtUtils.createAccessToken(userDetails);
            final String refreshToken = jwtUtils.createRefreshToken(userDetails);
            return new RLoginUser(dbUser, accessToken, refreshToken);
        } catch (Exception e) {
            System.out.println("login exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RRefreshToken refreshToken(String auth) throws Exception {
        try {
            String username = jwtUtils.extractRefreshUsername(JWTFilter.getTokenWithoutBearer(auth));

            // authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getBilkentId(), user.getPassword()));

            final UserDetails userDetails = jwtUserService.loadUserByUsername(username);
            final String accessToken = jwtUtils.createAccessToken(userDetails);
            return new RRefreshToken(accessToken);
            
        } catch (Exception e) {
            System.out.println("refresh token exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean changePassword(String auth, ChangePassword passwords) throws Exception {
        try {
            String username = jwtUtils.extractAccessUsername(JWTFilter.getTokenWithoutBearer(auth));
            User dbUser = accountRepository.findUserByBilkentId(Long.parseLong(username));

            if (dbUser == null) {
                throw new Exception("user is not found");
            }
            
            String hashedPassword = dbUser.getPassword();
            // username is bilkentId in this context
            
            
            // String hashedPassword = accountRepository.getPasswordIfUserExist(Long.parseLong(username));
            boolean passwordMatch = bCryptPasswordEncoder.matches(passwords.getOldPassword(), hashedPassword);

            if (!passwordMatch) {
                System.out.println("passwords does not match");
                throw new Exception("pasword no match");
            }

            System.out.println("passwords are matched");
            // hash new password
            String hashedNewPassword = bCryptPasswordEncoder.encode(passwords.getNewPassword());
            
            dbUser.setPassword(hashedNewPassword);
            int result = accountRepository.updatePassword(hashedNewPassword, dbUser.getId());
            // accountRepository.deleteById(dbUser.getId());
            // accountRepository.save(dbUser);
            System.out.println("result: " + result);
            return true;
            
        } catch (Exception e) {
            System.out.println("password change exception exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User addUser(User user) throws Exception {
        try {
            System.out.println("user that will be saved: " + user);
            boolean userExist = accountRepository.existsByBilkentIdOrEmail(user.getBilkentId(), user.getEmail());
            
            if (userExist) {
                throw new Exception("user already exists");
            } else {
                // generate uuid and hash password if user does not exist in the system
                user.setId(UUID.randomUUID());
                user.setPassword(encodePassword(user.getPassword()));

                System.out.println("user_id: " + user.getId());
                User newUser = accountRepository.save(user);

                System.out.println("user_id after register: " + accountRepository.findUserByEmail(user.getEmail()).get().getId());
                return newUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RRegisterStaff addStaff(RegisterStaff staff) throws Exception {
        try {
            String generatedPassword = StudentPlacementService.generatePsw();
            
            User newUser = addUser(new User(UUID.randomUUID(), staff.getName(), staff.getSurname(), staff.getEmail(), staff.getBilkentId(), generatedPassword, staff.getUserType()));        

            // check if staff has necessary info
            if (newUser.getUserType() == UserType.COORDINATOR || newUser.getUserType() == UserType.INSTRUCTOR) {
                if (staff.getDepartment() == null) {
                    throw new Exception(newUser.getUserType() + " should have a department");
                }
                
                if (staff.getFaculty() == null) {
                    throw new Exception(newUser.getUserType() + " should have a faculty");
                }
            } else if (newUser.getUserType() == UserType.FAC_MEMBER && staff.getFaculty() == null) {
                throw new Exception(newUser.getUserType() + " should have a faculty");                
            }

            // save staff to database
            Staff staffDb = Staff.toStaff(new StaffRequest(newUser.getId(), staff.getDepartment(), staff.getFaculty()), newUser);
            Staff savedStaff = staffRepository.save(staffDb);
            RInstructorCourseAdd responseInstructorSavedCourses = null;
            
            // if staff is user, add instructor course
            if (newUser.getUserType() == UserType.INSTRUCTOR) {
                if (staff.getBilkentCourseCodes() != null) {
                    responseInstructorSavedCourses = instructorCourseService.addCourseToInstructor(new InstructorCourseAdd(newUser.getId(), staff.getBilkentCourseCodes()));                    
                } else {
                    System.out.println("instructor has no course");
                }
            }

            // set unhashed password to user so that frontend can show it
            savedStaff.getUser().setPassword(generatedPassword);
            return new RRegisterStaff(savedStaff, responseInstructorSavedCourses);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User addUserWithUserRequest(UserRequest userRequest) throws Exception {
        try {
            // Convert the User request to User
            User user = User.toUser(userRequest, null);
            UUID id = UUID.randomUUID();
            user.setId(id);
            
            // Generate a random password
            String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                    StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            user.setPassword(encodePassword(password));
            System.out.println("In account 5");
            accountRepository.save(user);
            System.out.println("In account 6");
            return user;
        } catch (Exception e) {
            throw e;
        }
        
    }

    @Override
    public HashSet<User> addUserChunk(User[] users) throws Exception {
        HashSet<User> usersSet = new HashSet<>(Arrays.asList(users));
        HashSet<User> removedUsers = new HashSet<>();

        for (User user : usersSet) {
            try {
                boolean userExist = accountRepository.existsByBilkentId(user.getBilkentId());

                if (userExist) {
                    usersSet.remove(user);
                    removedUsers.add(user);
                } else {
                    user.setId(UUID.randomUUID());
                    // user.setPassword(user.getPassword());
                    user.setPassword(encodePassword(user.getPassword()));
                }
            } catch (Exception e) {
                throw e;
            }
        }

        try {
            accountRepository.saveAll(usersSet);
        } catch (Exception e) {
            System.out.println("users not saved: " + removedUsers);
            throw e;
        }

        return usersSet;
    }

    @Override
    public List<User> getUsers() {
        try {
            return accountRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    private String encodePassword(String plainPassword) {
        try {
            return bCryptPasswordEncoder.encode(plainPassword);
        } catch (Exception e) {
            throw e;
        }       
    }
}