package com.beam.beamBackend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.response.RLoginUser;
import com.beam.beamBackend.response.RRefreshToken;
import com.beam.beamBackend.response.RUserList;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.response.ResponseId;
import com.beam.beamBackend.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AccountController {
    private final AccountService accountService;

    // @Autowired
    // public AccountController(AccountService accountService) {
    //     this.accountService = accountService;
    // }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLogin userInfo) {
        try {
            RLoginUser token = accountService.login(userInfo);
            return Response.create("login is successful", HttpStatus.OK, token);
        } catch (Exception e) {
            return Response.create("login failed", 499); // might change later
        }        
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "register")
    public ResponseEntity<Object> register(@Valid @RequestBody User userInfo) {
        try {
            ResponseId ids = accountService.addUser(userInfo);
            return Response.create("account is created", HttpStatus.OK, ids);
        } catch (Exception e) {
            return Response.create("account creation is failed", HttpStatus.CONFLICT); // might change later
        }        
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/register_chunk")
    public ResponseEntity<HttpStatus> register(@RequestBody User[] userInfo) {
        try {
            accountService.addUserChunk(userInfo);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.FOUND); // might change later
        }        
    }

    @GetMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        try {
            RRefreshToken newToken = accountService.refreshToken(auth);
            return Response.create("new accsess token is created", HttpStatus.OK, newToken);
        } catch (Exception e) {
            return Response.create("auth failed", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @GetMapping("/test")
    public ResponseEntity<Object> testAuth() {
        try {
            String test = "welcome to the authenticated endpoint!";
            return Response.create("ok", HttpStatus.OK, test);
        } catch (Exception e) {
            return Response.create("auth failed", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUsers() {
        try {
            RUserList userList = accountService.getUsers();
            return Response.create("ok", HttpStatus.OK, userList);
        } catch (Exception e) {
            return Response.create("accounts cannot be retrieved", HttpStatus.OK); // might change later
        }        
    }
}
