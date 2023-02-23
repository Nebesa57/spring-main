package com.example.kyrsach.pojo;

import lombok.Data;

@Data
public class CommentsDto {
    private String text;
    private UserDto userDto;
    private UserDto ownerDto;
}
