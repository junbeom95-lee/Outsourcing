package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentGetResponse {
    private final Long commentId;
    private final String content;
    private final Long taskId;
    private final Long userId;
    private final User user; // user 정보 가져오기
    private final Long parentId;  // parentId 없을 경우 parentId 필드 제외
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentGetResponse(Long commentId, String content, Long taskId, Long userId, User user, Long parentId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.content = content;
        this.taskId = taskId;
        this.userId = userId;
        this.user = user;
        this.parentId = parentId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Comment 객체 CommentGetResponse로 변환하는 메서드
    public static CommentGetResponse fromGet(Comment comment) {
        Long parentId = null;
        if (comment.getParentComment() != null) {
            parentId = comment.getParentComment().getCommentId();
        }
        return new CommentGetResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                comment.getUser(),
                parentId,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
