package com.example.kyrsach.controllers;


import com.example.kyrsach.models.Comments;
import com.example.kyrsach.models.Friends;
import com.example.kyrsach.models.User;
import com.example.kyrsach.repository.FriendsRepository;
import com.example.kyrsach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class FriendsController {

    List<Friends> friends;
    List<User> users;

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
        return friendsRepository.save(new Friends(userRepository.findById(id).get(),userRepository.findById(twoID).get()));
    }


    //Yes
    @GetMapping(value = "allfriends/{id}")
    public List AllFriends( @PathVariable("id") Long id) {
        users = new ArrayList<>();
        friends = new ArrayList<>();
        users.add(userRepository.findById(id).get());
        friendsRepository.findAllByYouStr(users.get(0)).forEach(friends::add);
        return friends;
    }


    //YES
    @DeleteMapping(value = "delete/you/{id}/friends/{id2}")
    public List deleteFrineds(@PathVariable("id") Long id, @PathVariable("id2") Long id2){
        users = new ArrayList<>();
        friends = new ArrayList<>();
        users.add(userRepository.findById(id).get());
        users.add(userRepository.findById(id2).get());
        friends.add(friendsRepository.findByYouStrAndFriends(users.get(0),users.get(1)).get());
        friendsRepository.deleteById(friends.get(0).getId());
        return friends;
    }

}
