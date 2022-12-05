package com.beam.beamBackend.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.service.AccountService;

@RestController
@RequestMapping("api/v1/auth")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
        System.out.println("alooooooooooooooooooooooooooooooamkcocu calis sikicem");
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody User userInfo) {
        try {
            accountService.addUser(userInfo);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.FOUND); // might change later
        }        
    }

    @PostMapping("kakis")
    public ResponseEntity<String> test() {
        try {
            System.out.println("olleyyyy");
            return ResponseEntity.ok("test basairli merhabe dunya!!");
        } catch (Exception e) {
            return ResponseEntity.ok("HttpStatus.FOUND"); // might change later
        }        
    }

    @GetMapping()
    public ResponseEntity<String> test1() {
        System.out.println("mal");
        try {
            System.out.println("olleyyyy");
            return ResponseEntity.ok("test basairli merhabe dunya!!");
        } catch (Exception e) {
            return ResponseEntity.ok("HttpStatus.FOUND"); // might change later
        }        
    }

    @PostMapping("/register_chunk")
    public ResponseEntity<HttpStatus> register(@RequestBody User[] userInfo) {
        try {
            accountService.addUserChunk(userInfo);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.FOUND); // might change later
        }        
    }
}
