package com.example.kyrsach.mappers;

import com.example.kyrsach.models.Message;

import com.example.kyrsach.pojo.MessageDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;




@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "author",target = "userDto")
    MessageDto toDTOList(Message message);
}
