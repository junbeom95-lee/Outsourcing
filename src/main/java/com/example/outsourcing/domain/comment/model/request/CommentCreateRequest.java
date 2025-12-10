package com.example.outsourcing.domain.comment.model.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;
    @Nullable
    private Long parentId;
}
