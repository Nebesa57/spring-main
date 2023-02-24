package com.example.kyrsach.controllers;

import com.example.kyrsach.mappers.CommentsMapper;
import com.example.kyrsach.mappers.MessageMapper;
import com.example.kyrsach.repository.CommentsRepository;
import com.example.kyrsach.repository.MessageRepository;
import com.example.kyrsach.service.CommentsService;
import com.example.kyrsach.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BaseControllerTest {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:14.4")
            .withDatabaseName("example_db")
            .withUsername("Test")
            .withPassword("Test");

    @Container
    public static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:6.2-alpine")).withExposedPorts(6379);


    @BeforeAll
    public static void setUp(){
        container.withReuse(true);
        container.withInitScript("/Database/db.sql");
        container.start();
        redis.start();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
    }


    @Test
    public void testCreateUser(){

    }




    @AfterAll
    public static void tearDown(){
        container.stop();
        redis.stop();
    }
}