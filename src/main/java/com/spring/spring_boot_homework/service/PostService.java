package com.spring.spring_boot_homework.service;

import com.spring.spring_boot_homework.models.Post;
import com.spring.spring_boot_homework.models.PostPasswordRequestDto;
import com.spring.spring_boot_homework.models.PostRepository;
import com.spring.spring_boot_homework.models.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Boolean updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
        return post.update(requestDto);
    }

    public Boolean deletePost(Long id, PostPasswordRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")
        );
        if(Objects.equals(post.getPassword(), requestDto.getPassword())){
           postRepository.deleteById(id);
           return true;
        }else{
            return false;
        }
    }
}
