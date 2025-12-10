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
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @Query("select count(t) from Task t")
    int countAll();

    @Query("select count(t) from Task t where t.status = :status")
    int countByTasks(@Param("status") TaskStatus status);

    @Query("select count(t) from Task t where t.dueDate < :today")
    int countByOverDueDate(@Param("today") LocalDateTime today);

    @EntityGraph(attributePaths = "assignee")
    @Query("select count(t) from Task t where t.assignee.id = :id")
    double countByMyTasks(@Param("id")Long id);

    @EntityGraph(attributePaths = "assignee")
    @Query("select count(t) from Task t where t.assignee.id = :id and t.status = :status")
    double countByMyTasksByStatusIdDone(@Param("id")Long id,@Param("status") TaskStatus status);

    @Query("select t from Task t join fetch t.assignee a where a.id = :id and t.status = :status")
    List<Task> findTaskByMyStatus(@Param("id")Long id,@Param("status") TaskStatus status);

    @Query("select DAYOFWEEK(createdAt) as day, count(*) as cnt from Task group by DAYOFWEEK(createdAt)")
    List<Integer> countByCreatedByDay();

    @Query("select count(t) from Task t where DATE(t.dueDate) = :date")
    int countTasksDueDate(@Param("date")LocalDate date);

    @Query("select count(t) from Task t where DATE(t.dueDate) = :date and t.status = :status")
    int countTasksDueDateByStatus(@Param("date")LocalDate date,TaskStatus status);

}
