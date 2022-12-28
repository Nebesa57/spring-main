package com.example.kyrsach.controllers;


import com.example.kyrsach.models.User;

import com.example.kyrsach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("user/{id}")
    public User getUser(@PathVariable("id") Long id){
        return userRepository.findById(id).get();
    }


}
