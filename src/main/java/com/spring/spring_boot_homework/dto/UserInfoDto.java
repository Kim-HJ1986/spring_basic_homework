package com.spring.spring_boot_homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}
