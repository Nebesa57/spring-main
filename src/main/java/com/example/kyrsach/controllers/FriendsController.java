package com.example.kyrsach.controllers;


import com.example.kyrsach.models.Friends;
import com.example.kyrsach.models.User;
import com.example.kyrsach.repository.FriendsRepository;
import com.example.kyrsach.repository.UserRepository;
import com.example.kyrsach.service.CommentsService;
import com.example.kyrsach.service.FriendsService;
import com.example.kyrsach.service.MessageService;
import com.example.kyrsach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class FriendsController {

    List<Friends> friends;
    List<User> users;

    private final CommentsService commentsService;
    private final MessageService messageService;
    private final UserService userService;
    private final FriendsService friendsService;
    @Autowired
    FriendsRepository friendsRepository;
    @Autowired
    UserRepository userRepository;

    //Yes
    @PostMapping(value = "you/{userid}/add/{polzid}")
    public Friends createFriends ( @PathVariable("userid") Long id, @PathVariable("polzid") Long pozid){
        return addFriends(id,pozid);
    }
    //Yes
    private Friends addFriends(Long id, Long twoID){
        return friendsRepository.save(new Friends(userService.findById(id).get(),userRepository.findById(twoID).get()));
    }


    //Yes
    @GetMapping(value = "allfriends/{id}")
    public List<Friends> AllFriends( @PathVariable("id") Long id) {
        users = new ArrayList<>();
        friends = new ArrayList<>();
        users.add(userRepository.findById(id).get());
        friendsService.findAllByYouStr(users.get(0)).forEach(friends::add);
        return friends;
    }


    //YES
    @DeleteMapping(value = "delete/you/{id}/friends/{id2}")
    public List<Friends> deleteFrineds(@PathVariable("id") Long id, @PathVariable("id2") Long id2){
        users = new ArrayList<>();
        friends = new ArrayList<>();
        users.add(userService.findById(id).get());
        users.add(userService.findById(id2).get());
        friends.add(friendsService.findByYouStrAndFriends(users.get(0),users.get(1)).get());
        friendsRepository.deleteById(friends.get(0).getId());
        return friends;
    }
    @GetMapping("currentUser")
    public User getCurrentUser(@RequestParam Long id){
        return userService.findById(id).get();
    }
}
