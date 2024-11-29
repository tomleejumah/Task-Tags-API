package com.example.taskmanagement.Repository;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for managing Task entities. It extends the JpaRepository interface,
 * which provides CRUD operations and additional query methods.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    /**
     * Finds a Task entity by its ID.
     *
     * @param taskId The unique identifier of the Task entity to find.
     * @return An Optional containing the found Task entity if it exists, or an empty Optional if not found.
     */
    @Query("SELECT t FROM Task t WHERE t.id = :taskId")
    Optional<Task> findByTaskId(@Param("taskId") Long taskId);

    /**
     * Finds all Task entities based on their completion status.
     *
     * @param isCompleted The completion status to filter Task entities by.
     * @return A List containing all Task entities that match the given completion status.
     */
    @Query("SELECT t FROM Task t WHERE t.completed = :isCompleted")
    List<Task> findAllByCompleted(@Param("isCompleted") boolean isCompleted);

}
