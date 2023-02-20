package com.example.kyrsach.controllers;


import com.example.kyrsach.mappers.UserMapper;
import com.example.kyrsach.models.User;

import com.example.kyrsach.pojo.UserDto;
import com.example.kyrsach.repository.UserRepository;
import com.example.kyrsach.service.CommentsService;
import com.example.kyrsach.service.MessageService;
import com.example.kyrsach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    @GetMapping("user/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        return UserMapper.INSTANCE.toDTO(user);
    }


}
