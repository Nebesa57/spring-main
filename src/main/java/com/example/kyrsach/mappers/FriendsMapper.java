package com.example.kyrsach.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendsMapper {
    FriendsMapper INSTANCE = Mappers.getMapper(FriendsMapper.class);
}
