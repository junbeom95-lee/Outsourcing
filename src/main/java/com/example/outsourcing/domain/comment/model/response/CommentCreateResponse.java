package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponse {
    private final Long commentId;
    private final Long taskId;
    private final Long userId;
    private final User user; // user 정보 가져오기 + user 정보 필터링 추가 필요
    private final String content;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentCreateResponse(Long commentId, Long taskId, Long userId, User user, String content, Long parentId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.taskId = taskId;
        this.userId = userId;
        this.user = user;
        this.content = content;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Comment 객체로부터 CommentCreateResponse 객체 생성하는 메서드 추가
    public static CommentCreateResponse fromCreate(Comment commentSave) {
        // 부모 댓글 id 저장
        Long parentCommentId = null;
        if (commentSave.getParentComment() != null) {
            parentCommentId = commentSave.getParentComment().getCommentId();
        }

        return new CommentCreateResponse(
                commentSave.getCommentId(),
                commentSave.getTask().getId(),
                commentSave.getUser().getId(),
                commentSave.getUser(),  // 특정 유저 정보만 가져오도록 변경하기
                commentSave.getContent(),
                parentCommentId,
                commentSave.getCreatedAt(),
                commentSave.getUpdatedAt()
        );
    }
}