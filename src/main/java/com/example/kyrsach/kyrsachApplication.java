package com.example.kyrsach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@SpringBootApplication
public class kyrsachApplication {
	public static void main(String[] args) {
		SpringApplication.run(kyrsachApplication.class, args);
	}

}
