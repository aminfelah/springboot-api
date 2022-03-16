package com.example.main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.controllers.AuthController;

@SpringBootApplication
@ComponentScan(basePackageClasses = AuthController.class)
@EntityScan("com.example.models")
@ComponentScan({"com"})
@EnableJpaRepositories("com.example.repositories")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	
}
