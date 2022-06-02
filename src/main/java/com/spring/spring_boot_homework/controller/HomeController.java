package com.spring.spring_boot_homework.controller;

import com.spring.spring_boot_homework.model.UserRoleEnum;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //일반 로그인을 해도 UserDetails를 생성하여 UserDetailsImpl을 생성하여 Authentication으로 리턴
    //카카오 로그인을 하면 User 객체 생성 -> UserDetails를 생성하여 일반 로그인 방식과 같이 UserDetailsImpl을 받을 수 있고
    //구글 로그인을 하면 OAuth2User 타입으로 UserDetailsImpl를 만들어 Authentication으로 리턴하여 생성
    @GetMapping("/")
    public String showHomepage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("username", userDetails.getUsername());

        if (userDetails.getUser().getRole() == UserRoleEnum.ADMIN){
            model.addAttribute("admin_role", true);
        }
        return "index";
    }
}
