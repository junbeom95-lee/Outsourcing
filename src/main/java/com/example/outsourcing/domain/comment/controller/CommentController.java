package com.example.outsourcing.domain.comment.controller;

import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.auth.model.dto.UserinfoDetails;
import com.example.outsourcing.domain.comment.model.request.CommentCreateRequest;
import com.example.outsourcing.domain.comment.model.request.CommentUpdateRequest;
import com.example.outsourcing.domain.comment.model.response.CommentCreateResponse;
import com.example.outsourcing.domain.comment.model.response.CommentGetResponse;
import com.example.outsourcing.domain.comment.model.response.CommentUpdateResponse;
import com.example.outsourcing.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
@Slf4j
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommonResponse<CommentCreateResponse>> createComment(
            @PathVariable Long taskId,
            @AuthenticationPrincipal UserinfoDetails userDetails,
            @RequestBody CommentCreateRequest request) {
        CommonResponse<CommentCreateResponse> response = commentService.createComment(userDetails.getUserId(), taskId, request.getContent(), request.getParentId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 댓글 조회
    @GetMapping("/{taskId}/comments")
    public ResponseEntity<CommonResponse<PagedModel<CommentGetResponse>>> getComments(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") Integer page,  // 페이지 0
            @RequestParam(defaultValue = "10") Integer size,  // 페이지 크기 10
            @RequestParam(defaultValue = "newest") String sort  // 기본값 최신순
    ) {
        CommonResponse<PagedModel<CommentGetResponse>> response = commentService.getComments(taskId, page, size, sort);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 수정
    @PutMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentUpdateResponse>> commentUpdate(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserinfoDetails userDetails,
            @RequestBody CommentUpdateRequest request) {
        CommonResponse<CommentUpdateResponse> response = commentService.updateComment(userDetails.getUserId(), commentId, taskId, request.getContent());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> commentDelete(
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserinfoDetails userDetails) {
        CommonResponse<Void> response = commentService.deleteComment(userDetails.getUserId(), commentId, taskId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
