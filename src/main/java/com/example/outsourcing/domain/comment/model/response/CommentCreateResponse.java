package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponse {
    private final Long commentId;
    private final Long taskId;
    private final Long userId;
    private final CommentUserSimpleResponse user;
    private final String content;
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentCreateResponse(Long commentId, Long taskId, Long userId, CommentUserSimpleResponse user, String content, Long parentId, LocalDateTime createdAt, LocalDateTime updatedAt) {
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

        CommentUserSimpleResponse commentUserSimpleResponse= CommentUserSimpleResponse.fromUser(commentSave.getUser());

        return new CommentCreateResponse(
                commentSave.getCommentId(),
                commentSave.getTask().getId(),
                commentSave.getUser().getId(),
                commentUserSimpleResponse,  // 특정 유저 정보만 가져오도록 변경하기
                commentSave.getContent(),
                parentCommentId,
                commentSave.getCreatedAt(),
                commentSave.getUpdatedAt()
        );
    }
}