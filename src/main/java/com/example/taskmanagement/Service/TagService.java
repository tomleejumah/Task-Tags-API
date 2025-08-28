package com.example.taskmanagement.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.DTO.TagWithTasksDTO;
import com.example.taskmanagement.Model.Tag;
import com.example.taskmanagement.Model.Task;
import com.example.taskmanagement.Repository.TagRepository;
import com.example.taskmanagement.Repository.TaskRepository;
import com.example.taskmanagement.exception.types.ResourceNotFoundException;
import com.example.taskmanagement.response.ResponseHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

/**
 * This class provides services related to Tag operations.
 * It interacts with the TagRepository and TaskRepository to perform CRUD operations and retrieve data.
 */
@AllArgsConstructor
@Service
public class TagService {

    private final TaskService taskService;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;

    /**
     * Creates a new Tag in the database.
     *
     * @param tag A set of Tag objects to be created.
     * @return A ResponseEntity containing the HTTP status code and a message indicating the success or failure of the operation.
     *         If successful, it also includes the created Tag objects.
     */
    @Transactional
    public ResponseEntity<?> createTag(final Set<Tag> tag) {

        if (tag.isEmpty()) {
            return ResponseHandler.ResponseBuilder("Please provide at least one tag", HttpStatus.BAD_REQUEST);
        }
        final Set<Tag> processedTag = taskService.processedTag(tag);
        return ResponseHandler.ResponseBuilder("Tag Created successfully", HttpStatus.CREATED, processedTag);
    }

    /**
     * Retrieves a Tag along with its associated Tasks from the database.
     *
     * @param tagId The ID of the Tag to be retrieved.
     * @return A TagWithTasksDTO object containing the Tag and its associated Tasks.
     * @throws RuntimeException If the Tag with the given ID is not found.
     */
    @Transactional
    public TagWithTasksDTO getTagWithTasks(final Long tagId) {
        final Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with ID: " + tagId));

        final List<Long> taskIds = getTaskIdsForTag(tagId);
        final List<Task> tasks = taskRepository.findAllById(taskIds);

        return new TagWithTasksDTO(tag.getId(), tag.getName(), tasks);
    }

    /**
     * Retrieves a list of all Tags along with their associated task counts from the database.
     *
     * @return A list of TagDTO objects containing the Tag ID, name, and task count.
     */
    @Transactional
    public List<TagDTO> getTagsWithTaskCounts() {
        final List<Tag> tags = getAllTags();
        final List<TagDTO> tagWithTaskCounts = new ArrayList<>();

        for (final Tag tag : tags) {
            final int taskCount = getTaskCountForTag(tag.getId());
            tagWithTaskCounts.add(new TagDTO(tag.getId(), tag.getName(), (long) taskCount, tag.getDateCreated()));
        }

        return tagWithTaskCounts;
    }

    /**
     * Deletes a Tag from the database by its ID without affecting associated Tasks.
     *
     * @param tagId The ID of the Tag to be deleted.
     * @return A ResponseEntity containing the HTTP status code and a message indicating the success or failure of the operation.
     */
    @Transactional
    public ResponseEntity<?> deleteTag(final Long tagId) {
        final Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with ID: " + tagId));

        // Disassociate tasks from the tag
        final List<Task> tasks = taskRepository.findAllById(getTaskIdsForTag(tagId));
        for (final Task task : tasks) {
            task.getTags().remove(tag);
            taskRepository.save(task);
        }

        tagRepository.delete(tag);

        return ResponseHandler.ResponseBuilder(
                "Tag deleted successfully without affecting associated tasks",
                HttpStatus.OK
        );
    }


    /**
     * Retrieves all Tags from the database.
     *
     * @return A list of Tag objects.
     */
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    /**
     * Retrieves the count of Tasks associated with a given Tag from the database.
     *
     * @param tagId The ID of the Tag.
     * @return The count of Tasks associated with the Tag.
     */
    public int getTaskCountForTag(final Long tagId) {
        return tagRepository.countTasksByTagId(tagId);
    }

    /**
     * Retrieves the IDs of Tasks associated with a given Tag from the database.
     *
     * @param tagId The ID of the Tag.
     * @return A list of Task IDs associated with the Tag.
     */
    public List<Long> getTaskIdsForTag(final Long tagId) {
        return tagRepository.findTaskIdsByTagId(tagId);
    }

    public ResponseEntity<?> getAllTagsOrByFilterDateCreated(final LocalDate dateCreated) {
        if (dateCreated == null) {
            return ResponseHandler.ResponseBuilder("All tags with task count",
                    HttpStatus.OK,getTagsWithTaskCounts());
        }else {
           return ResponseHandler.ResponseBuilder("Filtered by date",HttpStatus.OK, tagRepository.findTagsByDateCreated(dateCreated));
        }
    }
}
