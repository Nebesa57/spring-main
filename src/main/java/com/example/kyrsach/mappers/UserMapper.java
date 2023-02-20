package com.example.kyrsach.mappers;

import com.example.kyrsach.models.User;
import com.example.kyrsach.pojo.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toDTO(User user);
}
