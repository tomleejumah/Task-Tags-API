package com.example.taskmanagement.Repository;

import com.example.taskmanagement.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Optional<Task> findByTaskId(@Param("taskId") Long taskId);

    @Query("SELECT t FROM Task t WHERE t.completed = :isCompleted")
    List<Task> findAllByCompleted(@Param("isCompleted") boolean isCompleted);
}
