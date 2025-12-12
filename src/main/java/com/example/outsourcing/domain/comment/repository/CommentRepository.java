package com.example.outsourcing.domain.comment.repository;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
    SELECT c 
    FROM Comment c
    WHERE c.task = :task
    ORDER BY 
        COALESCE(c.parentComment.id, c.id) asc,
        c.id asc
    """)
    Page<Comment> findCommentsByTaskWithParentCommentAsc(Task task, Pageable pageable);  // 오래된 순 정렬

    @Query("""
    SELECT c 
    FROM Comment c
    WHERE c.task = :task
    ORDER BY 
        COALESCE(c.parentComment.id, c.id) DESC, 
        c.id asc
    """)
    Page<Comment> findCommentsByTaskWithParentCommentDesc(Task task, Pageable pageable);  // 최신순 정렬
}

