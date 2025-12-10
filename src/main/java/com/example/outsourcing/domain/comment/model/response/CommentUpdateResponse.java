package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponse {
    private final Long commentId;
    private final Long taskId;
    private final Long userId;
    private final String content;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentUpdateResponse(Long commentId, Long taskId, Long userId, String content, Long parentId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.taskId = taskId;
        this.userId = userId;
        this.content = content;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Comment 객체로부터 CommentUpdateResponse 객체 생성하는 메서드 추가
    public static CommentUpdateResponse fromUpdate(Comment updatedComment) {
        // 부모 댓글 id 저장
        Long parentCommentId = null;
        if (updatedComment.getParentComment() != null) {
            parentCommentId = updatedComment.getParentComment().getCommentId();
        }
        return new CommentUpdateResponse(
                updatedComment.getCommentId(),
                updatedComment.getTask().getId(),
                updatedComment.getUser().getId(),
                updatedComment.getContent(),
                parentCommentId,
                updatedComment.getCreatedAt(),
                updatedComment.getUpdatedAt()
        );
    }

}
