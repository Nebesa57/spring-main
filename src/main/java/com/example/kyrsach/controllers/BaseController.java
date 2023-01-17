package com.example.kyrsach.controllers;


import com.example.kyrsach.models.Comments;
import com.example.kyrsach.models.Message;
import com.example.kyrsach.models.User;
import com.example.kyrsach.pojo.MessagePojo;
import com.example.kyrsach.repository.CommentsRepository;
import com.example.kyrsach.repository.MessageRepository;
import com.example.kyrsach.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public Message createMessage(@RequestBody MessagePojo newMessage, @PathVariable("id") Long id){
        return addMessage(newMessage,id);
    }
    private Message addMessage(MessagePojo message,Long id){
        return  messageRepository.save(new Message(message.getMessage(),userRepository.findById(id).get()));
    }
    //Yes
    @GetMapping(value = "messageAll")
    public List allMesage(){
        messages = new ArrayList<>();
        messageRepository.findAll().forEach(messages::add);
        return messages;
    }
    //Yes
    @GetMapping(value = "commentAll/{id}")
    public List allComments(@PathVariable("id") Long id){
        comments = new ArrayList<>();
        users = new ArrayList<>();
        users.add(userRepository.findById(id).get());
        commentsRepository.findAllByOwner(users.get(0)).forEach(comments::add);
        return comments;
    }


    //Yes
    @PostMapping(value = "comments/{userid}/polzovatel/{polzid}")
    public Comments createComments (@RequestBody MessagePojo newComments,  @PathVariable("userid") Long id, @PathVariable("polzid") Long pozid){
        return addComents(newComments,id,pozid);
    }
    private Comments addComents(MessagePojo comments,Long id, Long twoID){
        return commentsRepository.save(new  Comments(comments.getComment(),userRepository.findById(id).get(),userRepository.findById(twoID).get()));
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message message(Message message) {
        return messageRepository.save(message);
    }
}
