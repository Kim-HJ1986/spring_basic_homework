package com.spring.spring_boot_homework.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReplyDto {
    private String content;
    private String username;
    private Long postNum;
}
