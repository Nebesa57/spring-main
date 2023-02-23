package com.example.kyrsach.mappers;

import com.example.kyrsach.models.Friends;
import com.example.kyrsach.models.Message;
import com.example.kyrsach.pojo.FriendsDto;
import com.example.kyrsach.pojo.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FriendsMapper {
    @Mapping(source = "friends",target = "friends")
    FriendsDto toDTO(Friends friends);
}
