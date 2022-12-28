package com.example.kyrsach.repository;

import com.example.kyrsach.models.Friends;
import com.example.kyrsach.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends,Long> {
    Optional<Friends> findAllById(Long id);
    List<Friends> findAllByYouStr(User user);

    Optional<Friends> findByYouStrAndFriends(User user,User user2);

}
