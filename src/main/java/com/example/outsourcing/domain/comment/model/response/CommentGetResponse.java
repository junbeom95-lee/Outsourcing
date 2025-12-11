package com.example.outsourcing.domain.comment.model.response;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentGetResponse {
    private final Long commentId;
    private final String content;
    private final Long taskId;
    private final Long userId;
    private final CommentGetUserResponse user;
    private final Long parentId;  // parentId 없을 경우 parentId 필드 제외
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // Comment 객체 CommentGetResponse로 변환하는 메서드
    public static CommentGetResponse fromGet(Comment comment) {
        Long parentId = null;
        if (comment.getParentComment() != null) {
            parentId = comment.getParentComment().getId();
        }
        CommentGetUserResponse commentGetUserResponse = CommentGetUserResponse.fromUser(comment.getUser());
        return new CommentGetResponse(
                comment.getId(),
                comment.getContent(),
                comment.getTask().getId(),
                comment.getUser().getId(),
                commentGetUserResponse,
                parentId,
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    // 유저 정보 일부만 가져오는 클래스 생성
    @Getter
    @AllArgsConstructor
    private static class CommentGetUserResponse {
        private final Long id;
        private final String username;
        private final String name;
        private final String email;
        private final UserRole role;

        private  static CommentGetUserResponse fromUser(User user) {
            return new CommentGetUserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
        }
    }
}
