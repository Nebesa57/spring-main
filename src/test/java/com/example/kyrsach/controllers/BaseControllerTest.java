package com.example.kyrsach.controllers;

import com.example.kyrsach.mappers.CommentsMapper;
import com.example.kyrsach.mappers.MessageMapper;
import com.example.kyrsach.models.Message;
import com.example.kyrsach.models.User;
import com.example.kyrsach.pojo.MessageAndCommentsDto;
import com.example.kyrsach.pojo.MessageDto;
import com.example.kyrsach.pojo.UserDto;
import com.example.kyrsach.repository.CommentsRepository;
import com.example.kyrsach.repository.MessageRepository;
import com.example.kyrsach.service.CommentsService;
import com.example.kyrsach.service.UserService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.liquibase.enabled=false"
        })
@ContextConfiguration(initializers = {BaseControllerTest.Initializer.class})
public class BaseControllerTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.7-alpine")
            .withDatabaseName("mydb")
            .withUsername("myuser")
            .withPassword("mypass")
            .withInitScript("db.sql");
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }



















    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MessageRepository messageRepository;


    @Test
    public void testCreateUser(){
        ResponseEntity<UserDto> response = restTemplate.getForEntity("/api/user/1", UserDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserDto userDto = response.getBody();
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("admin", userDto.getUsername());
    }


    @Test
    public void testAllMessage() throws Exception {
        ResponseEntity<List<MessageDto>> response = restTemplate.exchange("/messageAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MessageDto>>() {
                });
        List<MessageDto> messageDtos = response.getBody();
        assertNotNull(messageDtos);
        assertEquals(1, messageDtos.size());
        assertEquals("hello", messageDtos.get(0).getText());
    }




}