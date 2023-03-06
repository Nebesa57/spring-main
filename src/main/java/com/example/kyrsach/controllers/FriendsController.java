package com.example.kyrsach.controllers;


import com.example.kyrsach.mappers.FriendsMapper;
import com.example.kyrsach.mappers.UserMapper;
import com.example.kyrsach.models.Friends;
import com.example.kyrsach.models.User;
import com.example.kyrsach.pojo.FriendsDto;
import com.example.kyrsach.pojo.UserDto;
import com.example.kyrsach.repository.FriendsRepository;
import com.example.kyrsach.repository.UserRepository;
import com.example.kyrsach.service.CommentsService;
import com.example.kyrsach.service.FriendsService;
import com.example.kyrsach.service.MessageService;
import com.example.kyrsach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class FriendsController {

    List<Friends> friends;
    List<User> users;

    List<FriendsDto> friendsDtos;

    private final FriendsMapper friendsMapper;
    private final UserMapper userMapper;

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
    public List<FriendsDto> AllFriends( @PathVariable("id") Long id) {
        try {
        users = new ArrayList<>();
        friends = new ArrayList<>();
        friendsDtos = new ArrayList<>();
        users.add(userRepository.findById(id).get());
        friendsService.findAllByYouStr(users.get(0)).forEach(friends::add);
        for(Friends friends1: friends){
            friendsDtos.add(friendsMapper.toDTO(friends1));
        }
        return friendsDtos;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
        }
    }


    //YES
    @DeleteMapping(value = "delete/you/{id}/friends/{id2}")
    public List<FriendsDto> deleteFrineds(@PathVariable("id") Long id, @PathVariable("id2") Long id2){
        users = new ArrayList<>();
        friends = new ArrayList<>();
        friendsDtos = new ArrayList<>();
        users.add(userService.findById(id).get());
        users.add(userService.findById(id2).get());
        friends.add(friendsService.findByYouStrAndFriends(users.get(0),users.get(1)).get());
        friendsDtos.add(friendsMapper.toDTO(friends.get(0)));
        friendsRepository.deleteById(friends.get(0).getId());
        return friendsDtos;
    }
    @GetMapping("currentUser/{id}")
    public UserDto getCurrentUser(@PathVariable("id") Long id){
        try {
        User user = userService.findById(id).get();
        return userMapper.toDTO(user);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
        }
    }
}
