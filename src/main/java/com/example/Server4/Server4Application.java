package com.example.Server4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.Server4")
@EnableJpaRepositories(basePackages = "com.example.Server4")
public class Server4Application {

	public static void main(String[] args) {
		SpringApplication.run(Server4Application.class, args);
	}

}
