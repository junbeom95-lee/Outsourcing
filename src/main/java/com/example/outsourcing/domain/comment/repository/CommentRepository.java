package com.example.outsourcing.domain.comment.repository;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 작업을 기준으로 조회
    Page<Comment> findByTask(Task task, Pageable pageable);
}
