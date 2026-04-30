package com.hospital.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"com.hospital"})
@EntityScan(basePackages = {"com.hospital"})
@EnableJpaRepositories(basePackages = {"com.hospital"})
public class MedistarApplication{

	public static void main(String[] args) {
		SpringApplication.run(MedistarApplication.class, args);
	}

}


