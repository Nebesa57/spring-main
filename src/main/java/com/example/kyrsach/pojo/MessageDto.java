package com.example.kyrsach.pojo;

import com.example.kyrsach.models.User;
import lombok.Data;

import javax.persistence.Entity;


@Data
public class MessageDto {
    private String text;
    private UserDto userDto;
}
