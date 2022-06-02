package com.spring.spring_boot_homework.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.spring_boot_homework.dto.UserInfoDto;
import com.spring.spring_boot_homework.exception.RestApiException;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import com.spring.spring_boot_homework.service.KakaoUserService;
import com.spring.spring_boot_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
<<<<<<< HEAD
    @ResponseBody // @ResponseBody 붙여주며 Response의 Body에 내용 담기. 예외 발생 시 핸들러에서 ResponseEntity()에 적절한 값 넣어서 보내준다.
>>>>>>> 3cc09b9c38f2b1222a082c4f2356159438442e8b
    @PostMapping("user/signup")
    public String registerUser(@RequestBody UserInfoDto requestDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.registerUser(requestDto, userDetails);
        return "정상";
    }

    //카카오 로그인
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code,
                             @AuthenticationPrincipal UserDetailsImpl userDetails)throws JsonProcessingException{
        if(userDetails != null){
            throw new IllegalStateException("이미 로그인이 되어있습니다.");
        }
        //authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);

        return "redirect:/";
    }


}
