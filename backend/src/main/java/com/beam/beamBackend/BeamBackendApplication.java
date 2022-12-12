package com.beam.beamBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.beam.beamBackend.model.CourseRequest;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// @Configuration
// @EnableAutoConfiguration// (exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
// @ComponentScan
@SpringBootApplication
// @EnableJpaRepositories
@EntityScan(basePackageClasses = {CourseRequest.class})
public class BeamBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeamBackendApplication.class, args);
	}
}
