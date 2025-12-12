package com.example.outsourcing.domain.comment.service;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.activity.util.ActivityLogSaveUtil;
import com.example.outsourcing.domain.comment.model.response.CommentCreateResponse;
import com.example.outsourcing.domain.comment.model.response.CommentGetResponse;
import com.example.outsourcing.domain.comment.model.response.CommentUpdateResponse;
import com.example.outsourcing.domain.comment.repository.CommentRepository;
import com.example.outsourcing.domain.task.repository.TaskRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ActivityLogSaveUtil activityLogSaveUtil;

    // 댓글 생성
    @Transactional
    public CommonResponse<CommentCreateResponse> createComment(Long userId, Long taskId, String content, Long parentId) {
        // 작업이 없는 경우 예외처리
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_TASK));
        Comment comment;  // 객체 선언

        // 사용자가 없는 경우 예외처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        // 댓글을 작성하지 않은 경우 예외처리
        if (content == null || content.isEmpty()) {
            throw new CustomException(ExceptionCode.REQUIRED_COMMENT);}

        // 부모 댓글이 없는 경우
        if (parentId == null) {
            comment = new Comment(user, task, content);
        } else {  // 부모 댓글이 있는 경우
            Comment parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_PARENT_COMMENT)); // parentId 예외처리);
            comment = new Comment(user, task, content);
            comment.parentComment = parentComment;  // 부모 댓글 할당
        }

        Comment commentSave = commentRepository.save(comment);  // 저장하기

        activityLogSaveUtil.saveActivityCommentCreate(commentSave.getTask().getId(), user, commentSave.getTask().getTitle());

        CommentCreateResponse response = CommentCreateResponse.fromCreate(commentSave);  // dto 생성
        return new CommonResponse<>(true, "댓글이 작성되었습니다.", response);  // 공통응답객체로 return
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public CommonResponse<PagedModel<CommentGetResponse>> getComments(Long taskId, Integer page, Integer size, String sort) {
        // 작업을 찾을 수 없는 경우 예외처리
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_TASK));

        // 기본 페이지 설정
        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> commentPage = null;

        // 정렬 조건 설정
        if ("oldest".equals(sort)) {
            pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());  // 오래된 순 정렬
            commentPage = commentRepository.findCommentsByTaskWithParentCommentAsc(task, pageable);
        } else if("newest".equals(sort)){
            pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());  // 최신순 정렬
            commentPage = commentRepository.findCommentsByTaskWithParentCommentDesc(task, pageable);
        }

        // 댓글을 CommentGetResponse로 변환
        Page<CommentGetResponse> commentGetResponsePage = commentPage.map(CommentGetResponse::fromGet);

        PagedModel<CommentGetResponse> response = new PagedModel<>(commentGetResponsePage);

        return new CommonResponse<>(true, "댓글 목록을 조회했습니다.", response);
    }


    // 댓글 수정
    @Transactional
    public CommonResponse<CommentUpdateResponse> updateComment(Long userId, Long commentId, Long taskId, String content) {
        // 댓글을 찾을 수 없는 예외처리
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_COMMENT));// 댓글이 없는 경우

        // 사용자를 찾을 수 없는 경우 예외처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        // 댓글 수정 권한 예외처리
        if (!comment.getUser().getId().equals(userId)){
            throw new CustomException(ExceptionCode.NOT_AUTHOR_UPDATE_COMMENT);}

        // 작업 찾을 수 없는 경우 예외처리
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new CustomException(ExceptionCode.NOT_FOUND_TASK));

        // 댓글 내용 비어있는 경우 예외처리
        if (content == null || content.isEmpty()) {
            throw new CustomException(ExceptionCode.REQUIRED_COMMENT);}

        comment.updateComment(content);  // 댓글 내용 수정
        Comment updateComment = commentRepository.save(comment); // 댓글 수정 후 저장

        activityLogSaveUtil.saveActivityCommentUpdate(updateComment.getTask().getId(), user);

        CommentUpdateResponse response = CommentUpdateResponse.fromUpdate(updateComment);
        return new CommonResponse<>(true, "댓글이 수정되었습니다.", response);
    }


    // 댓글 삭제
    @Transactional
    public CommonResponse<Void> deleteComment(Long userId, Long commentId, Long taskId) {
        // 사용자를 찾을 수 없는 경우 예외처리
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_USER));

        // 댓글을 찾을 수 없는 예외처리
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_COMMENT));

        // 댓글 삭제 권한 예외처리
        if(!comment.getUser().getId().equals(userId)) {
            throw new CustomException(ExceptionCode.NOT_AUTHOR_DELETE_COMMENT);}

        // 작업 찾을 수 없는 경우 예외처리
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(ExceptionCode.NOT_FOUND_TASK));

        // 댓글 삭제
        commentRepository.delete(comment);  // 부모 댓글일 경우 자식 자동 삭제됨

        activityLogSaveUtil.saveActivityCommentDelete(comment.getTask().getId(), user);

        return new CommonResponse<>(true, "댓글이 삭제되었습니다.", null);
    }
}
