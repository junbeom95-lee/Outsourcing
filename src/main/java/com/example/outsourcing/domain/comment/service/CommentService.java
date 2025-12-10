package com.example.outsourcing.domain.comment.service;


import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.model.CommonResponse;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    // 댓글 생성
    @Transactional
    public CommonResponse<CommentCreateResponse> createComment(Long userId, Long taskId, String content, Long parentId) {
        // 작업을 예외처리
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("작업을 찾을 수 없습니다."));
        Comment comment;  // 객체 선언

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 부모 댓글이 없는 경우
        if (parentId == null) {
            comment = new Comment(user, task, content);
        } else {  // 부모 댓글이 있는 경우
            Comment parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다.")); // parentId 예외처리);
            comment = new Comment(user, task, content);
            comment.parentComment = parentComment;  // 부모 댓글 할당
        }

        Comment commentSave = commentRepository.save(comment);  // 저장하기
        CommentCreateResponse response = CommentCreateResponse.fromCreate(commentSave);  // dto 생성
        return new CommonResponse<>(true, "댓글이 작성되었습니다.", response);  // 공통응답객체로 return
    }

}
