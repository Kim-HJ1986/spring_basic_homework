package com.spring.spring_boot_homework.controller;

import com.spring.spring_boot_homework.dto.ReplyDto;
import com.spring.spring_boot_homework.model.Reply;
import com.spring.spring_boot_homework.security.UserDetailsImpl;
import com.spring.spring_boot_homework.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/api/replies/{postNum}")
    public List<Reply> getReplies(@PathVariable Long postNum){
        return replyService.getReplies(postNum);
    }

    @PostMapping("/api/replies")
    public Reply createReply(@RequestBody ReplyDto replyDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return replyService.createReply(replyDto, userDetails);
    }

    @PutMapping("/api/replies/{id}")
    public Long updateReply(@RequestBody ReplyDto replyDto, @PathVariable Long id,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return replyService.updateReply(id, replyDto, userDetails);
    }

    @DeleteMapping("/api/replies/{id}")
    public Long deleteReply(@RequestBody String username, @PathVariable Long id,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        replyService.deleteReply(id, username, userDetails);
        return 1L;
    }
}
