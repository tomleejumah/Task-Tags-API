package com.example.taskmanagement.Service;

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

    @Transactional
    public ResponseEntity<?> createTask(Task task) {

        Set<Tag> savedTags = processedTag(task.getTags());
        if (savedTags.isEmpty()) {
            return ResponseHandler.ResponseBuilder("Please provide at least one tag", HttpStatus.BAD_REQUEST);
        }

        task.setTags(savedTags);

        try {
            Task savedTask = taskRepository.save(task);
            return ResponseHandler.ResponseBuilder("Task created successfully", HttpStatus.CREATED, savedTask);
        } catch (DataIntegrityViolationException e) {

            return ResponseHandler.ResponseBuilder("Error creating task: Duplicate or invalid data", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {

            return ResponseHandler.ResponseBuilder("Error creating task: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<?> deleteTask(Long id) {
         final Optional<Task> taskOptional = taskRepository.findByTaskId(id);
         if (taskOptional.isPresent()) {
             taskRepository.delete(taskOptional.get());
             return ResponseHandler.ResponseBuilder("task deleted successfully", HttpStatus.OK);
         }else return ResponseHandler.ResponseBuilder("task not found", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> updateTask(Long id, Task updatedTask) {
        return taskRepository.findByTaskId(id)
                .map(existingTask -> {
                    System.out.println("im available");
                    existingTask.setTittle(updatedTask.getTittle());
                    existingTask.setCompleted(updatedTask.isCompleted());
                    Set<Tag> savedTags = processedTag(updatedTask.getTags());
                    if (savedTags.isEmpty()) {
                        return ResponseHandler.ResponseBuilder("Please provide at least one tag", HttpStatus.BAD_REQUEST);
                    }

                    existingTask.getTags().clear();
                    existingTask.getTags().addAll(savedTags);
                    taskRepository.save(existingTask);
                    return ResponseHandler.ResponseBuilder("Task updated successfully", HttpStatus.OK, existingTask);
                })
                .orElse(ResponseHandler.ResponseBuilder("Task with id "+id+" not found", HttpStatus.NOT_FOUND));
    }


    private Set<Tag> processedTag(Set<Tag> tags) {
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


    @Transactional
    public List<Task> getAllTasksOrByFilterIsComplete(Optional<Boolean> isCompleted) {
        if (isCompleted.isPresent()) {
            return taskRepository.findAllByCompleted(isCompleted.get());
        } else {
            return taskRepository.findAll();
        }
    }

    @Transactional
    public ResponseEntity<?> uupdateTask(final Long id, Task task) {

        logger.info("This will appear in the logs");
        final Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            System.out.println("im available");
            Task existingTask = taskOptional.get();
            existingTask.setTittle(Optional.ofNullable(task.getTittle()).orElse(existingTask.getTittle()));
//            existingTask.setDescription(Optional.ofNullable(task.getDescription()).orElse(existingTask.getDescription()));
            existingTask.setCompleted(Optional.of(task.isCompleted()).orElse(false));
            try {
                taskRepository.save(existingTask);
                return ResponseHandler.ResponseBuilder("task updated successfully", HttpStatus.OK);
            }catch (RuntimeException e){
                return ResponseHandler.ResponseBuilder("Failed to update task", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return ResponseHandler.ResponseBuilder("Role with the id: "+id+" not found",HttpStatus.BAD_REQUEST);
        }
    }

//    public List<Task> getTasksByTag(String tag) {
//        return taskRepository.findByTagsContaining(tag);
//    }
}
