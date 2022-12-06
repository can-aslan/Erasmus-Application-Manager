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
	}
}
