package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentCreateResponse {
    private final Long commentId;
    private final Long taskId;
    private final Long userId;
    private final CommentCreateUserResponse user;
    private final String content;
    @JsonInclude(JsonInclude.Include.NON_NULL)  // parentId가 null이 아닌 경우에만 출력
    private final Long parentId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // Comment 객체로부터 CommentCreateResponse 객체 생성하는 메서드 추가
    public static CommentCreateResponse fromCreate(Comment commentSave) {
        // 부모 댓글 id 저장
        Long parentCommentId = null;
        if (commentSave.getParentComment() != null) {
            parentCommentId = commentSave.getParentComment().getId();
        }
        CommentCreateUserResponse commentCreateUserResponse = CommentCreateUserResponse.fromUser(commentSave.getUser());
        return new CommentCreateResponse(
                commentSave.getId(),
                commentSave.getTask().getId(),
                commentSave.getUser().getId(),
                commentCreateUserResponse,  // 특정 유저 정보만 가져옴
                commentSave.getContent(),
                parentCommentId,
                commentSave.getCreatedAt(),
                commentSave.getUpdatedAt()
        );
    }

    // 유저 정보 일부만 가져오는 클래스 생성
    @Getter
    @AllArgsConstructor
    private static class CommentCreateUserResponse {
        private final Long id;
        private final String username;
        private final String name;

        private static CommentCreateUserResponse fromUser(User user) {
            return new CommentCreateUserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getName()
            );
        }
    }
}