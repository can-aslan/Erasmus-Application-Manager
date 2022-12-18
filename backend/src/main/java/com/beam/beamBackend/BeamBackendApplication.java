package com.beam.beamBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.model.CourseWishlist;
import com.beam.beamBackend.model.CourseWishlistItem;
import com.beam.beamBackend.model.LearningAgreementForm;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.service.StudentPlacementService;

@SpringBootApplication
@EntityScan(basePackageClasses = {
	CourseRequest.class,
	CourseWishlist.class,
	CourseWishlistItem.class,
	User.class
})
public class BeamBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeamBackendApplication.class, args);
	}
}
