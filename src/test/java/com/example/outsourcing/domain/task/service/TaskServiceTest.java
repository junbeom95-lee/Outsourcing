package com.example.outsourcing.domain.task.service;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.enums.UserRole;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.activity.util.ActivityLogSaveUtil;
import com.example.outsourcing.domain.task.dto.request.TaskCreateRequest;
import com.example.outsourcing.domain.task.dto.response.TaskResponse;
import com.example.outsourcing.domain.task.repository.TaskRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ActivityLogSaveUtil activityLogSaveUtil;

    @Test
    /* 유저를 정상적으로 찾는다.
    Task 엔티티가 request의 값대로 생성된다.
    taskRepository.save()가 한 번 호출된다.
    activityLogSaveUtil.saveActivityTaskCreate()가 호출된다.
    응답값(TaskResponse)이 기대값과 일치한다. */
    void 태스크_생성_성공() {
        //given
        Long userId = 1L;

        TaskCreateRequest request = new TaskCreateRequest();
        ReflectionTestUtils.setField(request,"title","testTitle");
        ReflectionTestUtils.setField(request,"description","Test description");
        ReflectionTestUtils.setField(request,"priority","HIGH");
        ReflectionTestUtils.setField(request,"dueDate",LocalDateTime.now());
        ReflectionTestUtils.setField(request,"assigneeId", 1L);
        User test1 = new User("test1","test1@gamil.com","1234","test", UserRole.USER);
        given(userRepository.findById(1L)).willReturn(Optional.of(test1));

        Task savedTask = new Task(
                test1,
                "testTitle",
                "Test Description",
                TaskStatus.TODO,
                TaskPriority.HIGH,
                request.getDueDate()
        );
        given(taskRepository.save(any(Task.class))).willReturn(savedTask);
        //when
        CommonResponse<TaskResponse> response = taskService.createTask(userId,request);

        //then
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getMessage()).isEqualTo("작업이 생성되었습니다.");
        assertThat(response.getData().getTitle()).isEqualTo("testTitle");
    }

    @Test
    void 태스크_아이디가_안넘어왔다() {
        //given
        Long userId = null;

        given(userRepository.findById(userId)).willReturn(Optional.empty());
        TaskCreateRequest request = new TaskCreateRequest();
        ReflectionTestUtils.setField(request,"title","testTitle");
        ReflectionTestUtils.setField(request,"description","Test description");
        ReflectionTestUtils.setField(request,"priority","HIGH");
        ReflectionTestUtils.setField(request,"dueDate",LocalDateTime.now());
        //when & then
        CustomException e = assertThrows(CustomException.class,()-> taskService.createTask(userId,request));
        assertThat(e.getExceptionCode()).isEqualTo(ExceptionCode.NOT_FOUND_USER);

    }

    @Test
    void 조회하려는데_status_파라미터가_이상한값이다() {
        //given
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        String status = "someThingWeirdValue";
        String search = null;
        Long assigneeId = 1L;
        //when
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                ()-> taskService.getTasks(pageable, TaskStatus.valueOf(status), search, assigneeId)
        );
        //then
        assertThat(e.getMessage()).startsWith("No enum constant");
    }

}
