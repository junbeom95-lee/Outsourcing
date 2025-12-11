package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentUpdateResponse {
    private final Long commentId;
    private final Long taskId;
    private final Long userId;
    private final String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)  // parentId가 null이 아닌 경우에만 출력
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // Comment 객체로부터 CommentUpdateResponse 객체 생성하는 메서드 추가
    public static CommentUpdateResponse fromUpdate(Comment updatedComment) {
        // 부모 댓글 id 저장
        Long parentCommentId = null;
        if (updatedComment.getParentComment() != null) {
            parentCommentId = updatedComment.getParentComment().getId();
        }
        return new CommentUpdateResponse(
                updatedComment.getId(),
                updatedComment.getTask().getId(),
                updatedComment.getUser().getId(),
                updatedComment.getContent(),
                parentCommentId,
                updatedComment.getCreatedAt(),
                updatedComment.getUpdatedAt()
        );
    }
}
