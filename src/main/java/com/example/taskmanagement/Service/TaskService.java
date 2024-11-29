package com.example.taskmanagement.Service;

import com.example.taskmanagement.exception.types.ResourceNotFoundException;
import com.example.taskmanagement.exception.types.ValidationException;
import com.example.taskmanagement.Model.Tag;
import com.example.taskmanagement.Model.Task;
import com.example.taskmanagement.Repository.TagRepository;
import com.example.taskmanagement.Repository.TaskRepository;
import com.example.taskmanagement.response.ResponseHandler;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class TaskService {
    final Logger logger = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private TaskRepository taskRepository;
    private TagRepository tagRepository;

    /**
     * This function is responsible for creating a new task in the system.
     * It also ensures that at least one tag is associated with the task.
     *
     * @param task The task object to be created. It should contain the task details and associated tags.
     * @return Task The created task object.
     * @throws ValidationException If the task does not contain at least one tag.
     */
    @Transactional
    public Task createTask(Task task) {
        
        Set<Tag> savedTags = processedTag(task.getTags());
    
        if (savedTags.isEmpty()) {
            throw new ValidationException("Please provide at least one tag");
        }
    
        task.setTags(savedTags);
    
        return taskRepository.save(task);
    }

    /**
     * Deletes a task from the system based on the provided task ID.
     *
     * @param id The unique identifier of the task to be deleted.
     * @return ResponseEntity<?> A response entity indicating the outcome of the operation.
     *         It may contain a success message or an error message.
     *         The HTTP status code in the response entity will reflect the outcome.
     *         - HttpStatus.OK: If the task is successfully deleted.
     *         - HttpStatus.NOT_FOUND: If the task with the given ID is not found.
     */
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findByTaskId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));

        taskRepository.delete(task);
    }

    /**
     * Updates an existing task in the system based on the provided task ID and updated task object.
     *
     * @param id The unique identifier of the task to be updated.
     * @param updatedTask The updated task object containing the new details.
     *                    It should contain the task details and associated tags.
     *
     * @return The updated task object.
     *         If the task with the given ID is found and updated successfully, the updated task object is returned.
     *         If the task with the given ID is not found, a ResourceNotFoundException is thrown.
     *
     * @throws ValidationException If the updated task does not contain at least one tag.
     * @throws ResourceNotFoundException If the task with the given ID is not found.
     */
    @Transactional
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findByTaskId(id)
                .map(existingTask -> {
                    // Validate tags
                    Set<Tag> savedTags = processedTag(updatedTask.getTags());
                    if (savedTags.isEmpty()) {
                        throw new ValidationException("Please provide at least one tag");
                    }

                    // Update task details
                    existingTask.setTittle(updatedTask.getTittle());
                    existingTask.setCompleted(updatedTask.isCompleted());
                    existingTask.setDueDate(updatedTask.getDueDate());

                    existingTask.getTags().clear();
                    existingTask.getTags().addAll(savedTags);

                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
    }

    /**
     * Processes a set of tags to ensure they exist in the system.
     * If a tag does not exist, it is saved in the system.
     *
     * @param tags The set of tags to be processed.
     * @return A set of processed tags. If the input set is null or empty, an empty set is returned.
     *         Each processed tag is guaranteed to have a unique ID and name.
     */
    public Set<Tag> processedTag(Set<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Tag> savedTags = new HashSet<>();
        for (Tag tag : tags) {
            Tag savedTag = tagRepository.findByName(tag.getName())
                    .orElseGet(() -> tagRepository.save(tag));
            savedTags.add(savedTag);
        }
        return savedTags;
    }

    /**
     * Retrieves a list of tasks from the system based on the provided filter.
     * If the 'isCompleted' parameter is present, the function returns tasks that match the specified completion status.
     * If the 'isCompleted' parameter is not present, the function returns all tasks.
     *
     * @param isCompleted An optional boolean value representing the completion status of the tasks to be retrieved.
     *                    If present, the function returns tasks with the specified completion status.
     *                    If not present, the function returns all tasks.
     *
     * @return A list of tasks that match the specified filter.
     *         If the 'isCompleted' parameter is present, the list contains tasks with the specified completion status.
     *         If the 'isCompleted' parameter is not present, the list contains all tasks.
     *
     */
    @Transactional
    public List<Task> getAllTasksOrByFilterIsComplete(Optional<Boolean> isCompleted) {
        if (isCompleted.isPresent()) {
            return taskRepository.findAllByCompleted(isCompleted.get());
        } else {
            return taskRepository.findAll();
        }
    }
}
