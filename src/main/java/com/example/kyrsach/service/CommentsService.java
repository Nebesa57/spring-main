package com.example.kyrsach.service;

import com.example.kyrsach.models.Comments;
import com.example.kyrsach.models.User;
import com.example.kyrsach.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;

    public List<Comments> findAllByOwner(User user){
        return commentsRepository.findAllByOwner(user);
    }
}
