package com.spring.spring_boot_homework.controller;

import com.spring.spring_boot_homework.models.Post;
import com.spring.spring_boot_homework.models.PostRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class PostController {
    private final PostRepository postRepository;

    @GetMapping("/post")
    public String showPostDetail(Model model, @RequestParam("id") Long id){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
        model.addAttribute("post", post);
        return "post-detail";
    }
}
