package com.spring.spring_boot_homework.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Post extends Timestamped{

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)// 반드시 값을 가지도록 합니다.
    private String title;

    @Column(nullable = false)// 반드시 값을 가지도록 합니다.
    private String content;

    @Column(nullable = false)// 반드시 값을 가지도록 합니다.
    private String username;

    @JsonIgnore
    @Column(nullable = false)// 반드시 값을 가지도록 합니다.
    private String password;

    public Post(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
    }

    public Boolean update(PostRequestDto requestDto) {
        if (!Objects.equals(this.password, requestDto.getPassword())){
            return false;
        }else{
            this.title = requestDto.getTitle();
            this.content = requestDto.getContent();
            return true;
        }
    }
}
