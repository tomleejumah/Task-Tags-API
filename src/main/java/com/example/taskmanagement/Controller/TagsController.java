package com.example.taskmanagement.Controller;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.DTO.TagWithTasksDTO;
import com.example.taskmanagement.Model.Tag;
import com.example.taskmanagement.Model.Task;
import com.example.taskmanagement.Repository.TaskRepository;
import com.example.taskmanagement.Service.AuditLogService;
import com.example.taskmanagement.Service.TagService;
import com.example.taskmanagement.Service.TaskService;
import com.example.taskmanagement.response.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * Controller for handling operations related to tags.
 *
 * @author tomlee
 * @since 1.0
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tags")
@AllArgsConstructor
@RestController
public class TagsController {

    /**
     * Service for handling audit logs.
     */
    private AuditLogService auditLogService;

    /**
     * Service for handling tag operations.
     */
    private TagService tagService;

    /**
     * Creates a re-usable tag.
     *
     * @param tag The set of tags to be created.
     * @return ResponseEntity containing the created tag.
     */
    @Operation(summary = "Creates a re-useable tag", description = "Returns created tag")
    @ApiResponse(responseCode = "201", description = "tag created successfully",
            content = @Content(schema = @Schema(implementation = Task.class)))
    @PostMapping("/create/")
    public ResponseEntity<?> createTag(@RequestBody Set<Tag> tag) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.log("Tag", "CREATE", username, "Created a new tag");
        return tagService.createTag(tag);
    }

    /**
     * Retrieves a specific tag with its tasks.
     *
     * @param tagId The ID of the tag to be retrieved.
     * @return ResponseEntity containing the tag with its tasks.
     */
    @Operation(summary = "Retrieves a specific tag with its tasks", description = "Returns tag with tasks")
    @ApiResponse(responseCode = "200", description = "operation successful",
            content = @Content(schema = @Schema(implementation = TagWithTasksDTO.class)))
    @GetMapping("/{tagId}")
    public ResponseEntity<?> getTagWithTasks(@PathVariable("tagId") Long tagId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.log("Tag", "READ", username, "Retrieved tag with tasks");
        TagWithTasksDTO tagWithTasksDTO = tagService.getTagWithTasks(tagId);
        return ResponseHandler.ResponseBuilder("success", HttpStatus.OK,tagWithTasksDTO);
    }

    /**
     * Retrieves all tags with their tasks.
     *
     * @return List of TagDTO containing tags with their tasks.
     */
    @Operation(summary = "Retrieves all tags with their tasks", description = "Returns list of tags with tasks")
    @ApiResponse(responseCode = "200", description = "operation successful",
            content = @Content(schema = @Schema(implementation = List.class, type = "array")))
    @GetMapping("/all")
    public List<TagDTO> getAllTags() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.log("Tag", "READ", username, "Retrieved all tags");
        return tagService.getTagsWithTaskCounts();
    }
}
