package com.example.outsourcing.domain.task.service;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.ExceptionCode;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.exception.CustomException;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.task.dto.request.TaskCreateRequest;
import com.example.outsourcing.domain.task.dto.request.TaskStatusChangeRequest;
import com.example.outsourcing.domain.task.dto.request.TaskUpdateRequest;
import com.example.outsourcing.domain.task.dto.response.TaskResponse;
import com.example.outsourcing.domain.task.repository.TaskRepository;
import com.example.outsourcing.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public CommonResponse<Page<TaskResponse>> getTasks(Pageable pageable, TaskStatus status, String search, Long assigneeId) {
        Page<Task> tasks;
        TaskStatus taskStatus = null;

        if (status != null) {
            try {
                taskStatus = TaskStatus.valueOf(String.valueOf(status));
            } catch (Exception e) {
                throw new CustomException(ExceptionCode.BAD_REQUEST_PARAMETER);
            }
        }
        if (assigneeId != null && !userRepository.existsById(assigneeId)) {
            throw new CustomException(ExceptionCode.BAD_REQUEST_PARAMETER);
        }
        //status 있고, assigneeId 있음
        if (status != null && assigneeId != null) {
            tasks = taskRepository.findByStatusAndAssigneeId(status, assigneeId, pageable);
        }
        //status 있고, search 있음
        else if (status != null && search != null) {
            tasks = taskRepository.findBySearch(search, pageable);
        }
        //status만 있음
        else if (status != null) {
            tasks = taskRepository.findByStatus(status, pageable);
        }
        else {
            tasks = Page.empty(pageable);
        }

        Page<TaskResponse> content = tasks.map(TaskResponse::from);

        return new CommonResponse<>(true,"작업 목록 조회 성공", content);

    }

    @Transactional(readOnly = true)
    public CommonResponse<TaskResponse> getTaskSingle(Long id) {

        Task content = taskRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_TASK));

        return new CommonResponse<>(true, "작업 조회 성공",TaskResponse.fromDetail(content));
    }

    @Transactional
    public CommonResponse<TaskResponse> createTask(Long userId, TaskCreateRequest request) {
        //베어럴 토큰 정보 필요함

        if (request.getTitle() == null || request.getDescription() == null) {
            throw new CustomException(ExceptionCode.BAD_REQUEST_CREATE_TASK);
        }

        User assignee = userRepository.findById(userId).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_USER));

        Task task = new Task(
                assignee,
                request.getTitle(),
                request.getDescription(),
                request.getTaskStatus(),
                request.getTaskPriority(),
                request.getDueDate()
        );
        taskRepository.save(task);

        return new CommonResponse<>(true, "작업이 생성되었습니다.", TaskResponse.from(task));
    }

    @Transactional
    public CommonResponse<TaskResponse> updateTask(Long userId, Long id, TaskUpdateRequest request) {
        //id 검증 로직
        User assignee = taskRepository.findByAssignee_Id(userId).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_USER));

        Task task = taskRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_TASK));
        if (!assignee.getId().equals(task.getAssignee().getId())) {
            throw new CustomException(ExceptionCode.NOT_AUTHOR_TASK);
        }
        task.update(request);

        Task savedTask = taskRepository.save(task);

        return new CommonResponse<>(true, "작업이 수정되었습니다.",TaskResponse.from(savedTask));
    }

    @Transactional
    public CommonResponse<Void> deleteTask(Long userId, Long id) {
        //id 검증 로직
        User assignee = userRepository.findById(userId).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_USER));
        Task task = taskRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_TASK));
        if (!assignee.getId().equals(task.getAssignee().getId())) {
            throw new CustomException(ExceptionCode.NOT_AUTHOR_TASK);
        }
        taskRepository.deleteById(id);

        return new CommonResponse<>(true, "작업이 삭제되었습니다.",null);
    }

    @Transactional
    public CommonResponse<TaskResponse> changeTaskStatus(Long userId, Long id, TaskStatusChangeRequest request) {
        User assignee = userRepository.findById(userId).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_USER));
        Task task = taskRepository.findById(id).orElseThrow(()-> new CustomException(ExceptionCode.NOT_FOUND_TASK));
        if (!assignee.getId().equals(task.getAssignee().getId())) {
            throw new CustomException(ExceptionCode.NOT_AUTHOR_TASK);
        }

       TaskStatus status = request.getTaskStatus();

        task.updateStatus(status);
        taskRepository.save(task);
        return new CommonResponse<>(true, "작업 상태가 변경되었습니다.",TaskResponse.from(task));
    }

}
