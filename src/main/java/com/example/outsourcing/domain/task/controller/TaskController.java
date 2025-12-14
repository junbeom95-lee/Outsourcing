package com.example.outsourcing.domain.task.controller;

import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.common.model.CommonResponse;
import com.example.outsourcing.domain.task.dto.request.TaskCreateRequest;
import com.example.outsourcing.domain.task.dto.request.TaskStatusChangeRequest;
import com.example.outsourcing.domain.task.dto.request.TaskUpdateRequest;
import com.example.outsourcing.domain.task.dto.response.TaskResponse;
import com.example.outsourcing.domain.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    //작업 목록 조회 (페이징, 필터링)
    @GetMapping
    public ResponseEntity<CommonResponse<Page<TaskResponse>>> getTasks(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(required = false) TaskStatus status,
                                                                       @RequestParam(required = false) String search,
                                                                       @RequestParam(required = false) Long assigneeId) {
        Pageable pageable =  PageRequest.of(page, size);

        return ResponseEntity.ok(taskService.getTasks(pageable, status, search, assigneeId));
    }

    //작업 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TaskResponse>> getTaskSingle(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskSingle(id));
    }

    //작업 생성
    @PostMapping
    public ResponseEntity<CommonResponse<TaskResponse>> createTask(@AuthenticationPrincipal Long userId,
                                                                   @Valid @RequestBody TaskCreateRequest request) {
        return ResponseEntity.ok(taskService.createTask(userId, request));
    }

    //작업 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<TaskResponse>> updateTask(@AuthenticationPrincipal Long userId,
                                                                   @PathVariable Long id,
                                                                   @Valid @RequestBody TaskUpdateRequest request) {
        return ResponseEntity.ok(taskService.updateTask(userId, id, request));
    }

    //작업 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTask(@AuthenticationPrincipal Long userId,
                                                           @PathVariable Long id) {
        return ResponseEntity.ok(taskService.deleteTask(userId, id));
    }

    //작업 상태 변경
    @PatchMapping("{id}/status")
    public ResponseEntity<CommonResponse<TaskResponse>> changeTaskStatus(@AuthenticationPrincipal Long userId,
                                                                         @PathVariable Long id,
                                                                         @Valid @RequestBody TaskStatusChangeRequest request) {
        return ResponseEntity.ok(taskService.changeTaskStatus(userId, id, request));
    }
}

