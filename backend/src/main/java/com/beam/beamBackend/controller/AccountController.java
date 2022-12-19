package com.beam.beamBackend.controller;

import java.util.List;
import java.util.HashSet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.model.RegisterStaff;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.request.ChangePassword;
import com.beam.beamBackend.response.RLoginUser;
import com.beam.beamBackend.response.RRefreshToken;
import com.beam.beamBackend.response.RRegisterStaff;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AccountController {
    private final IAccountService accountService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLogin userInfo) {
        try {
            RLoginUser token = accountService.login(userInfo);
            return Response.create("login is successful", HttpStatus.OK, token);
        } catch (Exception e) {
            return Response.create(e.getLocalizedMessage() + " : " + e.getMessage(), 499); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "register")
    public ResponseEntity<Object> register(@Valid @RequestBody User userInfo) {
        try {
            User ids = accountService.addUser(userInfo);
            return Response.create("account is created", HttpStatus.OK, ids);
        } catch (Exception e) {
            return Response.create(e.getLocalizedMessage() + " : " + e.getMessage(), HttpStatus.CONFLICT); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/register_chunk")
    public ResponseEntity<Object> register(@RequestBody User[] userInfo) {
        try {
            HashSet<User> users = accountService.addUserChunk(userInfo);
            return Response.create("accounts are created", HttpStatus.OK, users);
        } catch (Exception e) {
            return Response.create("account creation is failed", HttpStatus.BAD_REQUEST); // might change later
        }        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "register_staff")
    public ResponseEntity<Object> registerStaff(@Valid @RequestBody RegisterStaff userInfo) {
        try {
            RRegisterStaff savedStaff = accountService.addStaff(userInfo);
            return Response.create("account is created", HttpStatus.OK, savedStaff);
        } catch (Exception e) {
            return Response.create("account creation is failed", HttpStatus.CONFLICT); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/change_password")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePassword userInfo,
        @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {

        try {
            accountService.changePassword(auth, userInfo);
            return Response.create("password is changed", HttpStatus.OK);
        } catch (Exception e) {
            return Response.create("password cannot be changed", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        try {
            RRefreshToken newToken = accountService.refreshToken(auth);
            return Response.create("new accsess token is created", HttpStatus.OK, newToken);
        } catch (Exception e) {
            return Response.create("auth failed", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/test")
    public ResponseEntity<Object> testAuth() {
        try {
            String test = "welcome to the authenticated endpoint!";
            return Response.create("ok", HttpStatus.OK, test);
        } catch (Exception e) {
            return Response.create("auth failed", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/")
    public ResponseEntity<Object> getUsers() {
        try {
            List<User> userList = accountService.getUsers();
            return Response.create("ok", HttpStatus.OK, userList);
        } catch (Exception e) {
            return Response.create("accounts cannot be retrieved", HttpStatus.OK); // might change later
        }        
    }
}
