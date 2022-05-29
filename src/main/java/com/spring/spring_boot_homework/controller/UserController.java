package com.spring.spring_boot_homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.spring_boot_homework.dto.UserInfoDto;
import com.spring.spring_boot_homework.service.KakaoUserService;
import com.spring.spring_boot_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    //로그인 페이지 호출
    @GetMapping("/user/login")
    public String showLoginPage(){
        return "login";
    }

    //회원가입 페이지 호출
    @GetMapping("/user/signup")
    public String showSignupPage(){
        return "signup";
    }

    //회원가입 신청
    @PostMapping("user/signup")
    public String registerUser(@ModelAttribute UserInfoDto requestDto){
        userService.registerUser(requestDto);
        return "redirect:/user/login";
    }

    //카카오 로그인
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code)throws JsonProcessingException{
        //authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);

        return "redirect:/";
    }

}
