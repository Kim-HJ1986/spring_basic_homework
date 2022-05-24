package com.spring.spring_boot_homework.controller;

import com.spring.spring_boot_homework.models.Post;
import com.spring.spring_boot_homework.models.PostPasswordRequestDto;
import com.spring.spring_boot_homework.models.PostRepository;
import com.spring.spring_boot_homework.models.PostRequestDto;
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
    public String updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto){
        if(postService.updatePost(id, requestDto)){
            return "게시글이 수정되었습니다.";
        }else{
            return "비밀번호가 일치하지 않습니다.";
        }
    }

    @DeleteMapping("/api/posts/{id}")
    public String deletePost(@PathVariable Long id, @RequestBody PostPasswordRequestDto requestDto){
        if(postService.deletePost(id, requestDto)){
            return "게시글이 삭제되었습니다.";
        }else{
            return "비밀번호가 일치하지 않습니다.";
        }
    }
}
