package com.example.outsourcing.domain.comment.controller;

import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.comment.model.request.CommentCreateRequest;
import com.example.outsourcing.domain.comment.model.request.CommentUpdateRequest;
import com.example.outsourcing.domain.comment.model.response.CommentCreateResponse;
import com.example.outsourcing.domain.comment.model.response.CommentGetResponse;
import com.example.outsourcing.domain.comment.model.response.CommentUpdateResponse;
import com.example.outsourcing.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/api/tasks/{taskId}/comments")
    public ResponseEntity<CommonResponse<CommentCreateResponse>> createComment(
            @PathVariable Long taskId,
            @AuthenticationPrincipal Long user,
            @RequestBody CommentCreateRequest request) {
        log.info("debug1");
        CommonResponse<CommentCreateResponse> response = commentService.createComment(user, taskId, request.getContent(), request.getParentId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
