package com.example.kyrsach.mappers;


import com.example.kyrsach.models.Comments;
import com.example.kyrsach.pojo.CommentsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    @Mapping(source = "author",target = "userDto")
    @Mapping(source = "author",target = "ownerDto")
    CommentsDto toDTO(Comments comments);
}
