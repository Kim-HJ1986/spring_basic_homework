package com.spring.spring_boot_homework.models;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private String username;
    private String password;
}
