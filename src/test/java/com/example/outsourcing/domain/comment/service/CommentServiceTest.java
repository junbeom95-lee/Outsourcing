package com.example.outsourcing.domain.comment.service;

import com.example.outsourcing.common.entity.Comment;
import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.activity.util.ActivityLogSaveUtil;
import com.example.outsourcing.domain.comment.model.response.CommentCreateResponse;
import com.example.outsourcing.domain.comment.model.response.CommentGetResponse;
import com.example.outsourcing.domain.comment.model.response.CommentUpdateResponse;
import com.example.outsourcing.domain.comment.repository.CommentRepository;
import com.example.outsourcing.domain.task.repository.TaskRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedModel;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // mock 사용 어노테이션
class CommentServiceTest {
    @Mock  // 가짜 객체
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ActivityLogSaveUtil activityLogSaveUtil;

    @InjectMocks
    private CommentService commentService;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long DEFAULT_COMMENT_ID = 1L;

    private static final Long DEFAULT_PARENT_ID = null;
    private static final String DEFAULT_CONTENT = "홍길동1이 작성한 테스트댓글입니다.";

    private static final User DEFAULT_TESTUSER = new User("홍길동", "string@naver.com", "string1234", "홍길동", UserRole.USER);
    private static final Task DEFAULT_TESTTASK = new Task(DEFAULT_TESTUSER, "작업1", "작업1 설명", TaskStatus.TODO, TaskPriority.HIGH, LocalDateTime.now());
    private static final Comment DEFAULT_TESTCOMMENT = new Comment(DEFAULT_TESTUSER, DEFAULT_TESTTASK, DEFAULT_CONTENT);

    @Test
    @DisplayName("댓글 생성 - 유효한 사용자, 작업이 주어졌을 떄 - 성공 케이스")
    void createComment_success() {
        // given
        ReflectionTestUtils.setField(DEFAULT_TESTUSER, "id", 1L);
        ReflectionTestUtils.setField(DEFAULT_TESTTASK, "id", 1L);
        ReflectionTestUtils.setField(DEFAULT_TESTCOMMENT, "id", 1L);

        // Mock 동작 정의
        when(userRepository.findById(DEFAULT_USER_ID)).thenReturn(Optional.of(DEFAULT_TESTUSER));
        when(taskRepository.findById(DEFAULT_TASK_ID)).thenReturn(Optional.of(DEFAULT_TESTTASK));
        when(commentRepository.save(any(Comment.class))).thenReturn(DEFAULT_TESTCOMMENT);

        // when
        CommonResponse<CommentCreateResponse> result = commentService.createComment(DEFAULT_USER_ID, DEFAULT_TASK_ID, DEFAULT_CONTENT, DEFAULT_PARENT_ID);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getData().getCommentId()).isEqualTo(DEFAULT_TESTCOMMENT.getId());  // 댓글 id 일치 확인
        assertThat(result.getData().getContent()).isEqualTo(DEFAULT_TESTCOMMENT.getContent());  // 댓글 내용 일치 확인
        assertThat(result.getData().getTaskId()).isEqualTo(DEFAULT_TESTTASK.getId());  // 작업 id 일치 확인
        assertThat(result.getData().getUserId()).isEqualTo(DEFAULT_TESTUSER.getId());  // 사용자 id 일치 확인
        assertThat(result.getMessage()).isEqualTo("댓글이 작성되었습니다.");  // 댓글 생성 메세지 확인하기

        verify(commentRepository, times(1)).save(any(Comment.class));  // 호출 횟수 검사
    }


    @Test
    @DisplayName("작업id로 댓글 오래된 순 조회 - 성공케이스")
    void getComments() {
        // given
        int page = 0;
        int size = 10;
        String sort = "oldest";

        ReflectionTestUtils.setField(DEFAULT_TESTUSER, "id", DEFAULT_USER_ID);
        ReflectionTestUtils.setField(DEFAULT_TESTTASK, "id", DEFAULT_TASK_ID);

        // 댓글 리스트
        List<Comment> commentList = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            Comment comment = new Comment(
                    DEFAULT_TESTUSER,
                    DEFAULT_TESTTASK,
                    DEFAULT_CONTENT + i  // 댓글 번호 매겨주기
            );
            commentList.add(comment);
        }

        // 사용자,작업,댓글의 id값 할당해주기
        for (int i = 0; i < commentList.size(); i++) {
            ReflectionTestUtils.setField(commentList.get(i), "id", (long) (i + 1));
        }

        // page 설정
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").ascending());
        Page<Comment> commentPage = new PageImpl<>(commentList, pageable, commentList.size());

        // Mock 동작 정의
        when(taskRepository.findById(DEFAULT_TASK_ID)).thenReturn(Optional.of(DEFAULT_TESTTASK));
        when(commentRepository.findCommentsByTaskWithParentCommentAsc(DEFAULT_TESTTASK, pageable)).thenReturn(commentPage);

        // when
        CommonResponse<PagedModel<CommentGetResponse>> result = commentService.getComments(DEFAULT_TASK_ID, page, size, sort);

        // then
        assertThat(result.getData().getContent()).hasSize(11);
        assertThat(result.getMessage()).isEqualTo("댓글 목록을 조회했습니다.");
    }


    @Test
    @DisplayName("댓글 수정 - 유효한 사용자, 작업이 주어졌을 때 - 성공 케이스")
    void updateComment() {
        // given
        // 댓글 처음에 작성한 사용자 정보
        Long userId = DEFAULT_USER_ID;
        Long commentId = 1L;
        Long taskId = DEFAULT_TASK_ID;
        String newContent = "수정된 댓글 내용입니다.";  // 댓글 내용 부분만 수정됨

        // id 값 할당해주기
        ReflectionTestUtils.setField(DEFAULT_TESTUSER, "id", 1L);
        ReflectionTestUtils.setField(DEFAULT_TESTTASK, "id", 1L);
        ReflectionTestUtils.setField(DEFAULT_TESTCOMMENT, "id", 1L);

        // 댓글 작성한 유저 정보, 댓글 객체 설정
        Comment originalComment = new Comment(DEFAULT_TESTUSER, DEFAULT_TESTTASK, "원래 댓글");
        ReflectionTestUtils.setField(originalComment, "id", commentId);

        // 수정된 댓글 객체
        Comment updatedComment = new Comment(DEFAULT_TESTUSER, DEFAULT_TESTTASK, newContent);  // 댓글 내용만 바뀜
        ReflectionTestUtils.setField(updatedComment, "id", commentId);

        // Mock 동작 정의
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(originalComment));
        when(userRepository.findById(DEFAULT_USER_ID)).thenReturn(Optional.of(DEFAULT_TESTUSER));
        when(taskRepository.findById(DEFAULT_TASK_ID)).thenReturn(Optional.of(DEFAULT_TESTTASK));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);  // 댓글 수정 후 저장된 객체 반환

        // when
        CommonResponse<CommentUpdateResponse> result = commentService.updateComment(userId, commentId, taskId, newContent);

        // then
        assertThat(result).isNotNull(); // 결과값이 Null인지 확인
        assertThat(result.getData().getCommentId()).isEqualTo(DEFAULT_COMMENT_ID);  // 댓글 ID 일치 확인
        assertThat(result.getData().getUserId()).isEqualTo(DEFAULT_USER_ID);  // 사용자 ID 일치 확인
        assertThat(result.getData().getTaskId()).isEqualTo(DEFAULT_TASK_ID);  // 작업 ID 일치 확인
        assertThat(result.getData().getContent()).isEqualTo(newContent);  // 바뀐 댓글 내용인지 확인하기
        assertThat(result.getMessage()).isEqualTo("댓글이 수정되었습니다.");  // 댓글 수정 메세지 확인하기

        verify(commentRepository, times(1)).save(any(Comment.class));  // 호출 횟수 검사
    }


    @Test
    @DisplayName("댓글 삭제 - 유효한 사용자, 작업이 주어졌을 때 - 성공 케이스")
    void deleteComment() {
        // given
        // 댓글 처음에 작성한 사용자 정보
        Long userId = DEFAULT_USER_ID;
        Long commentId = 1L;
        Long taskId = DEFAULT_TASK_ID;
        String newContent = "삭제된 댓글 내용입니다.";  // 댓글 내용 부분만 삭제됨

        // id 값 할당해주기
        ReflectionTestUtils.setField(DEFAULT_TESTUSER, "id", 1L);
        ReflectionTestUtils.setField(DEFAULT_TESTTASK, "id", 1L);
        ReflectionTestUtils.setField(DEFAULT_TESTCOMMENT, "id", 1L);

        // 댓글 작성한 유저 정보
        Comment originalComment = new Comment(DEFAULT_TESTUSER, DEFAULT_TESTTASK, "원래 댓글");
        ReflectionTestUtils.setField(originalComment, "id", commentId);

        // Mock 동작 정의
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(originalComment));
        when(userRepository.findById(DEFAULT_USER_ID)).thenReturn(Optional.of(DEFAULT_TESTUSER));
        when(taskRepository.findById(DEFAULT_TASK_ID)).thenReturn(Optional.of(DEFAULT_TESTTASK));

        // when
        CommonResponse<Void> result = commentService.deleteComment(userId, commentId, taskId);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).isEqualTo("댓글이 삭제되었습니다.");
        verify(commentRepository, times(1)).delete(any(Comment.class));
    }
}