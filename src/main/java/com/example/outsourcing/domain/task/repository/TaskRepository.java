package com.example.outsourcing.domain.task.repository;

import com.example.outsourcing.common.entity.Task;
import com.example.outsourcing.common.entity.User;
import com.example.outsourcing.common.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @EntityGraph(attributePaths = "assignee")
    @Query("select t " +
            "from Task t " +
            "where t.status =:status " +
            "and (:assigneeId is null or t.assignee.id = :assigneeId)")
    Page<Task> findByStatusAndAssigneeId(
            @Param("status") TaskStatus status,
            @Param("assigneeId") Long assigneeId,
            Pageable pageable
    );
    @EntityGraph(attributePaths = "assignee")
    @Query("select t from Task t where t.title = :search")
    Page<Task> findBySearch(@Param("search") String search, Pageable pageable);

    @EntityGraph(attributePaths = "assignee")
    @Query("select t from Task t where t.status = :status")
    Page<Task> findByStatus(@Param("status")TaskStatus status, Pageable pageable);

    @EntityGraph(attributePaths = "assignee")
    Optional<Task> findById(@Param("assigneeId") Long id);

    @EntityGraph(attributePaths = "assignee")
    @Query("select t from Task t where t.assignee.id = :assigneeId")
    Optional<User> findByAssignee_Id(@Param("assigneeId")Long assigneeId);
}
