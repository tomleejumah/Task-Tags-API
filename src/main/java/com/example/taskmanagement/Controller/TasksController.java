package com.example.taskmanagement.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.Model.Task;
import com.example.taskmanagement.Service.AuditLogService;
import com.example.taskmanagement.Service.TaskService;
import com.example.taskmanagement.response.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tasks")
@AllArgsConstructor
@Tag(name = "Task Management", description = "Operations for managing tasks")
@RestController
public class TasksController {

  private TaskService taskService;
  private AuditLogService auditLogService;

  /**
   * Gets all tasks or tasks filtered by 'isCompleted' status.
   */
  @Operation(summary = "Creates task with at-least one tag", description = "Returns created task")
  @ApiResponse(responseCode = "201", description = "task created successfully", content = @Content(schema = @Schema(implementation = Task.class)))
  @ApiResponse(responseCode = "500", description = "Internal server error occurred")
  @PostMapping("/create/")
  public ResponseEntity<?> createTask(@RequestBody Task task) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    auditLogService.log("Task", "CREATE", username, "Created a new task");
    taskService.createTask(task);
    return ResponseHandler.ResponseBuilder("Task created successfully", HttpStatus.CREATED, task);
  }

  /**
   * Updates a task with at-least one tag in the system.
   */
  @Operation(summary = "Updates task with at-least one tag", description = "Returns updated task")
  @ApiResponse(responseCode = "200", description = "operation successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
  @ApiResponse(responseCode = "500", description = "Internal server error occurred")
  @PutMapping("/update/{taskId}")
  public ResponseEntity<?> updateTask(@PathVariable("taskId") Long id, @RequestBody Task updatedTask) {
    Task updatedTaskResult = taskService.updateTask(id, updatedTask);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    auditLogService.log("Task", "UPDATE", username, "Updated a task");
    return ResponseHandler.ResponseBuilder("Update success", HttpStatus.OK, updatedTaskResult);
  }

  /**
   * Deletes a task by its ID.
   */
  @Operation(summary = "Deletes Task by ID", description = "Returns string of response weather success or failed")
  @ApiResponse(responseCode = "200", description = "operation successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
  @ApiResponse(responseCode = "500", description = "Internal server error occurred")
  @DeleteMapping("/delete/{taskId}")
  public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long id) {
    taskService.deleteTask(id);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    auditLogService.log("Task", "DELETE", username, "Deleted a task");
    return ResponseEntity.ok("Task deleted successfully");
  }

  /**
   * Retrieves a list of tasks based on the provided filter.
   * If no filter is provided, all tasks are returned.
   **/
  @Operation(summary = "Retrieves tasks", description = "Returns a list of tasks and can be filtered with is complete (true/false)")
  @ApiResponse(responseCode = "200", description = "operation successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class)))
  @GetMapping()
  public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false) Boolean completed) {
    List<Task> tasks = taskService.getAllTasksOrByFilterIsComplete(Optional.ofNullable(completed));
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    auditLogService.log("Task", "GET", username, "Retrieved tasks");
    return ResponseEntity.ok(tasks);
  }
}
