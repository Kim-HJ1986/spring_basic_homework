package com.spring.spring_boot_homework.model;

import com.spring.spring_boot_homework.dto.ReplyDto;
import com.spring.spring_boot_homework.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Reply extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @Column()
    private Long postNum;

    public Reply(ReplyDto replyDto){
        this.content = replyDto.getContent();
        this.username = replyDto.getUsername();
        this.postNum = replyDto.getPostNum();
    }

    public Long update(ReplyDto replyDto) {
        this.content = replyDto.getContent();
        return 1L;
    }
}
