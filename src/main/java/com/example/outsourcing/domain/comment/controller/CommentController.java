package com.example.outsourcing.domain.comment.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.comment.model.request.CommentCreateRequest;
import com.example.outsourcing.domain.comment.model.request.CommentUpdateRequest;
import com.example.outsourcing.domain.comment.model.response.CommentCreateResponse;
import com.example.outsourcing.domain.comment.model.response.CommentGetResponse;
import com.example.outsourcing.domain.comment.model.response.CommentUpdateResponse;
import com.example.outsourcing.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        CommonResponse<CommentCreateResponse> response = commentService.createComment(user, taskId, request.getContent(), request.getParentId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 댓글 조회
    @GetMapping("/api/tasks/{taskId}/comments")
    public ResponseEntity<CommonResponse<List<CommentGetResponse>>> getComments(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") Integer page,  // 페이지 0
            @RequestParam(defaultValue = "10") Integer size,  // 페이지 크기 10
            @RequestParam(defaultValue = "newest") String sort  // 기본값 최신순
    ) {
        CommonResponse<List<CommentGetResponse>> response = commentService.getComments(taskId, page, size, sort);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/api/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentUpdateResponse>> commentUpdate(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long user,
            @RequestBody CommentUpdateRequest request) {
        CommonResponse<CommentUpdateResponse> response = commentService.updateComment(user, commentId, taskId, request.getContent());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/api/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> commentDelete(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long user) {
        CommonResponse<Void> response = commentService.deleteComment(user, commentId, taskId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
