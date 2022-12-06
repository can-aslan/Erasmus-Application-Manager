package com.beam.beamBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication

public class BeamBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeamBackendApplication.class, args);
		System.out.println("yubii");
	}

	@GetMapping()
    public ResponseEntity<String> helo() {
        System.out.println("mal");
        try {
            System.out.println("olleyyyy");
            return ResponseEntity.ok("test basairli merhabe dunya!!");
        } catch (Exception e) {
            return ResponseEntity.ok("HttpStatus.FOUND"); // might change later
        }        
    }

}
