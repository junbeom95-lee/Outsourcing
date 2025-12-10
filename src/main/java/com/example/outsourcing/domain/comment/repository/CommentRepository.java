package com.example.outsourcing.domain.comment.repository;

import com.example.outsourcing.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {


}
