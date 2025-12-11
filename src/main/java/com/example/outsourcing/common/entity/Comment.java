package com.example.outsourcing.common.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;   // 댓글 id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 유저 ID (외래키로 가져옴)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;   // 작업 ID (외래키로 가져옴)

    @Column(nullable = false, length = 100)
    private String content;  // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public Comment parentComment;  // 부모 댓글 (자기자신 참조)

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true) // 부모 댓글 삭제시 자동 삭제됨
    private List<Comment> childComment = new ArrayList<>();  // 자식 댓글 (대댓글)

    public Comment(User user, Task task, String content) {
        this.user = user;
        this.task = task;
        this.content = content;
        this.parentComment = null;
    }

    // 댓글 수정
    public void updateComment(String content) {
        this.content = content;
    }
}