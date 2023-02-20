package com.example.kyrsach.service;

import com.example.kyrsach.models.Message;
import com.example.kyrsach.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final  MessageRepository messageRepository;

    Optional<Message> findBy(){
      return messageRepository.findBy();
    }

}
