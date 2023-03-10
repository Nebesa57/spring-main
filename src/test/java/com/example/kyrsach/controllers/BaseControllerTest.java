package com.example.kyrsach.controllers;


import com.example.kyrsach.mappers.FriendsMapper;
import com.example.kyrsach.mappers.UserMapper;
import com.example.kyrsach.models.Friends;
import com.example.kyrsach.models.Message;
import com.example.kyrsach.models.User;
import com.example.kyrsach.pojo.MessageAndCommentsDto;
import com.example.kyrsach.pojo.MessageDto;
import com.example.kyrsach.pojo.UserDto;
import com.example.kyrsach.repository.FriendsRepository;
import com.example.kyrsach.repository.MessageRepository;
import com.example.kyrsach.repository.UserRepository;
import com.example.kyrsach.service.FriendsService;
import com.example.kyrsach.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private FriendsController friendsController;
    @Autowired
    private FriendsRepository friendsRepository;

    @Autowired
    private FriendsService friendsService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendsMapper friendsMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TestRestTemplate restTemplate;


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
    public void testCreateMessages() throws Exception{
        User user = userService.findById(2L).get();
        Message message = new Message("hello",user);
        messageRepository.save(message);
        ResponseEntity<List<MessageDto>> response = restTemplate.exchange("/messageAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MessageDto>>() {
                });
        List<MessageDto> messageDtos = response.getBody();
        assertNotNull(messageDtos);
        assertEquals(1, messageDtos.size());
        assertEquals("hello", messageDtos.get(0).getText());
    }


    @Test
    public void testAllMessage() throws Exception {
        ResponseEntity<List<MessageDto>> response = restTemplate.exchange("/messageAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<MessageDto>>() {
                });
        List<MessageDto> messageDtos = response.getBody();
        assertNotNull(messageDtos);
        assertEquals(0, messageDtos.size());
    }


    @Test
    void testCreateMessage() throws Exception {

        User user = new User();
        user = userService.findById(1L).get();

        MessageAndCommentsDto newMessage = new MessageAndCommentsDto();
        newMessage.setMessage("Test message");
        ResponseEntity<Message> responseEntity = restTemplate.postForEntity("/message/" + user.getId(), newMessage, Message.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(newMessage.getMessage(), responseEntity.getBody().getText());
        assertEquals(user, responseEntity.getBody().getAuthor());

        // проверяем, что сообщение было сохранено в базе данных
        Message savedMessage = messageRepository.findById(responseEntity.getBody().getId().longValue()).get();
        assertEquals(newMessage.getMessage(), savedMessage.getText());
        assertEquals(user, savedMessage.getAuthor());
    }





}