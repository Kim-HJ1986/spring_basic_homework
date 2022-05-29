package com.spring.spring_boot_homework.repository;

import com.spring.spring_boot_homework.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPostNumOrderByModifiedAtDesc(Long postNum);
}
