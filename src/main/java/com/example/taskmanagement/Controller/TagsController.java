package com.example.taskmanagement.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanagement.DTO.TagWithTasksDTO;
import com.example.taskmanagement.Model.Tag;
import com.example.taskmanagement.Model.Task;
import com.example.taskmanagement.Service.AuditLogService;
import com.example.taskmanagement.Service.TagService;
import com.example.taskmanagement.response.ResponseHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;


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
   @GetMapping()
    public ResponseEntity<?> getAllTags(@RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateCreated) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.log("Tag", "READ", username, "Retrieved all tags");

        return tagService.getAllTagsOrByFilterDateCreated(dateCreated);
    }



    /**
     * Deletes a tag by its ID.
     *
     * @param tagId The ID of the tag to be deleted.
     * @return ResponseEntity containing success message.
     * */
    @Operation(summary = "Deletes a tag by its ID", description = "Returns success message")
    @ApiResponse(responseCode = "200", description = "operation successful")
    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable("tagId") Long tagId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.log("Tag", "DELETE", username, "Deleted tag with id: " + tagId);
        return tagService.deleteTag(tagId);
    }
}
