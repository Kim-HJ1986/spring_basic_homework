package com.spring.spring_boot_homework.service;

import com.spring.spring_boot_homework.dto.ReplyDto;
import com.spring.spring_boot_homework.model.Reply;
import com.spring.spring_boot_homework.repository.ReplyRepository;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<Reply> getReplies(Long postNum) {
        return replyRepository.findAllByPostNumOrderByModifiedAtDesc(postNum);
    }

    public Reply createReply(ReplyDto replyDto, UserDetailsImpl userDetails) {
        if(userDetails == null){
            throw new IllegalStateException("로그인이 필요한 기능입니다.");
        }
        if(replyDto.getContent().length() == 0){
            throw new IllegalStateException("댓글 내용을 입력해주세요");
        }
        Reply reply = new Reply(replyDto);
        return replyRepository.save(reply);
    }

    @Transactional
    public Long updateReply(Long id, ReplyDto replyDto, UserDetailsImpl userDetails) {
        if(!replyDto.getUsername().equals(userDetails.getUsername())){
            throw new IllegalStateException("본인의 댓글만 수정할 수 있습니다.");
        }
        Reply reply = replyRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 Id의 댓글이 존재하지 않습니다.")
        );
        return reply.update(replyDto);
    }

    public void deleteReply(Long id, String username, UserDetailsImpl userDetails) {
        if(!username.equals(userDetails.getUsername())){
            throw new IllegalStateException("본인의 댓글만 삭제할 수 있습니다.");
        }
        Reply reply = replyRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 Id의 댓글이 존재하지 않습니다.")
        );
        replyRepository.delete(reply);
    }
}
