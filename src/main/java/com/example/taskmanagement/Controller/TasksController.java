package com.example.taskmanagement.Controller;

import com.example.taskmanagement.Model.Task;
import com.example.taskmanagement.Service.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tasks")
@AllArgsConstructor
@NoArgsConstructor
@RestController
public class TasksController {

    @Autowired
    private TaskService taskService;


    @PostMapping("/create/")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable("taskId")  Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long id) {
        return taskService.deleteTask(id);
    }

/**
 * Retrieves a list of tasks based on the provided filter.
 * If no filter is provided, all tasks are returned.
 **/
    @GetMapping()
    public ResponseEntity<List<Task>> getTasks(@RequestParam(required = false) Boolean completed) {
        List<Task> tasks = taskService.getAllTasksOrByFilterIsComplete(Optional.ofNullable(completed));
        return ResponseEntity.ok(tasks);
    }
}
