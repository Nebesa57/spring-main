package com.example.kyrsach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class kyrsachApplication {
	public static void main(String[] args) {
		SpringApplication.run(kyrsachApplication.class, args);
	}

}
