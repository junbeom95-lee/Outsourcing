package com.example.outsourcing.common.entity;

import com.example.outsourcing.common.enums.TaskPriority;
import com.example.outsourcing.common.enums.TaskStatus;
import com.example.outsourcing.domain.task.dto.request.TaskUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name="task")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "task_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User assignee;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name="description", columnDefinition = "TEXT",nullable = false)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private TaskStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    private TaskPriority priority;
    @Column(name="due_date",nullable = false)
    private LocalDateTime dueDate;

    public Task(User assignee, String title, String description, TaskStatus status, TaskPriority priority, LocalDateTime dueDate) {
        this.assignee = assignee;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public void update(TaskUpdateRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        if (request.getStatus()!=null) {
            this.status = TaskStatus.valueOf(request.getStatus());
        }
        this.priority = request.getTaskPriority();
        this.dueDate = request.getDueDate();
    }

    public void updateStatus(TaskStatus status) {
        this.status = status;
    }



}
