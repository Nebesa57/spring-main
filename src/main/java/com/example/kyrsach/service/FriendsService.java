package com.example.kyrsach.service;

import com.example.kyrsach.models.Friends;
import com.example.kyrsach.models.User;
import com.example.kyrsach.repository.FriendsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;

    public List<Friends> findAllByYouStr(User user) {
        return friendsRepository.findAllByYouStr(user);
    }

    public Optional<Friends> findByYouStrAndFriends(User user, User user2) {
        return friendsRepository.findByYouStrAndFriends(user, user2);
    }
}
