package com.spring.spring_boot_homework.controller;

import com.spring.spring_boot_homework.model.Post;
import com.spring.spring_boot_homework.dto.PostPasswordRequestDto;
import com.spring.spring_boot_homework.repository.PostRepository;
import com.spring.spring_boot_homework.dto.PostRequestDto;
import com.spring.spring_boot_homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostRestController {

    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/api/posts")
    public List<Post> getPosts(){
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @GetMapping("/api/posts/{id}")
    public Post getPostById(@PathVariable Long id){
        return postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
    }

    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto){
        Post post = new Post(requestDto);
        return postRepository.save(post);
    }

    @PutMapping("/api/posts/{id}")
    public boolean updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/api/posts/{id}")
    public boolean deletePost(@PathVariable Long id, @RequestBody PostPasswordRequestDto requestDto){
        if(postService.deletePost(id, requestDto)){
            return true;
        }else{
            return false;
        }
    }

    @DeleteMapping("/api/posts")
    public void deleteAllPost(){
        postRepository.deleteAll();
    }
}
