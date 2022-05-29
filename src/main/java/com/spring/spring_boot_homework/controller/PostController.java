package com.spring.spring_boot_homework.controller;

import com.spring.spring_boot_homework.model.Post;
import com.spring.spring_boot_homework.repository.PostRepository;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class PostController {
    private final PostRepository postRepository;

    @GetMapping("/post")
    public String showPostDetail(Model model, @RequestParam("id") Long id,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
        model.addAttribute("post", post);
        model.addAttribute("username", userDetails.getUsername());
        return "post-detail";
    }
}
