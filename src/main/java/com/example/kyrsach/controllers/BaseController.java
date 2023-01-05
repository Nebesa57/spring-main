package com.example.kyrsach.controllers;


import com.example.kyrsach.models.Comments;
import com.example.kyrsach.models.Message;
import com.example.kyrsach.models.User;
import com.example.kyrsach.repository.CommentsRepository;
import com.example.kyrsach.repository.MessageRepository;
import com.example.kyrsach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping()
public class BaseController {
    @Autowired
    private MessageRepository messageRepository;

    List<Message> messages;
    List<Comments> comments;

    List<User> users;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    //Yes
    @PostMapping(value = "message/{id}")
    public Message createMessage(@RequestBody Message newMessage,  @PathVariable("id") Long id){
        return addMessage(newMessage,id);
    }
    private Message addMessage(Message message,Long id){
        return  messageRepository.save(new Message(message.getText(),userRepository.findById(id).get()));
    }
    //Yes
    @PostMapping(value = "messageAll")
    public List allMesage(){
        messages = new ArrayList<>();
        messageRepository.findAll().forEach(messages::add);
        return messages;
    }
    //Yes
    @PostMapping(value = "commentAll/{id}")
    public List allComments(@PathVariable("id") Long id){
        comments = new ArrayList<>();
        users = new ArrayList<>();
        users.add(userRepository.findById(id).get());
        commentsRepository.findAllByOwner(users.get(0)).forEach(comments::add);
        return comments;
    }


    //Yes
    @PostMapping(value = "comments/{userid}/polzovatel/{polzid}")
    public Comments createComments (@RequestBody Comments newComments,  @PathVariable("userid") Long id, @PathVariable("polzid") Long pozid){
        return addComents(newComments,id,pozid);
    }
    private Comments addComents(Comments comments,Long id, Long twoID){
        return commentsRepository.save(new  Comments(comments.getText(),userRepository.findById(id).get(),userRepository.findById(twoID).get()));
    }
}
