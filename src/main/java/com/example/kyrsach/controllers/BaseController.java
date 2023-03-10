package com.example.kyrsach.controllers;


import com.example.kyrsach.mappers.CommentsMapper;
import com.example.kyrsach.mappers.MessageMapper;
import com.example.kyrsach.models.Comments;
import com.example.kyrsach.models.Message;
import com.example.kyrsach.models.User;
import com.example.kyrsach.pojo.CommentsDto;
import com.example.kyrsach.pojo.MessageAndCommentsDto;
import com.example.kyrsach.pojo.MessageDto;
import com.example.kyrsach.repository.CommentsRepository;
import com.example.kyrsach.repository.MessageRepository;
import com.example.kyrsach.repository.UserRepository;
import com.example.kyrsach.service.CommentsService;
import com.example.kyrsach.service.MessageService;
import com.example.kyrsach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class BaseController {


    List<Message> messages;
    List<Comments> comments;
    List<MessageDto> messageDtos;
    List<User> users;
    List<CommentsDto> commentsDtos;
    @Autowired
    private MessageRepository messageRepository;
    private final CommentsService commentsService;

    private final CommentsMapper commentsMapper;
    private final MessageMapper messageMapper;
    @Autowired
    private final UserService userService;

    @Autowired
    private CommentsRepository commentsRepository;

    //Yes
    @PostMapping(value = "message/{id}")
    public Message createMessage(@RequestBody MessageAndCommentsDto newMessage, @PathVariable("id") Long id) {
        return addMessage(newMessage, id);
    }

    private Message addMessage(MessageAndCommentsDto message, Long id) {
        User user = userService.findById(id).get();
        return messageRepository.save(new Message(message.getMessage(), userService.findById(id).get()));
    }

    //Yes
    @GetMapping(value = "messageAll")
    public List<MessageDto> allMesage() {
        try {
            messages = new ArrayList<>();
            messageDtos = new ArrayList<>();
            messageRepository.findAll().forEach(messages::add);
            for (Message message : messages) {
                messageDtos.add(messageMapper.toDTOList(message));
            }
            return messageDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
        }
    }

    //Yes
    @GetMapping(value = "commentAll/{id}")
    public List<CommentsDto> allComments(@PathVariable("id") Long id) {
        try {
            comments = new ArrayList<>();
            users = new ArrayList<>();
            commentsDtos = new ArrayList<>();
            users.add(userService.findById(id).get());
            commentsService.findAllByOwner(users.get(0)).forEach(comments::add);
            for (Comments comment : comments) {
                commentsDtos.add(commentsMapper.toDTO(comment));
            }
            return commentsDtos;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
        }
    }


    //Yes
    @PostMapping(value = "comments/{userid}/polzovatel/{polzid}")
    @PreAuthorize("#id == #pozid")
    public Comments createComments(@RequestBody MessageAndCommentsDto newComments, @PathVariable("userid") Long id, @PathVariable("polzid") Long pozid) {
        return addComents(newComments, id, pozid);
    }

    private Comments addComents(MessageAndCommentsDto comments, Long id, Long twoID) {
        return commentsRepository.save(new Comments(comments.getComment(), userService.findById(id).get(), userService.findById(twoID).get()));
    }

    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public Message message(Message message) {
        return messageRepository.save(message);
    }
}
