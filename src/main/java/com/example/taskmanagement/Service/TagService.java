package com.example.taskmanagement.Service;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.DTO.TagWithTasksDTO;
import com.example.taskmanagement.Model.Tag;
import com.example.taskmanagement.Model.Task;
import com.example.taskmanagement.Repository.TagRepository;
import com.example.taskmanagement.Repository.TaskRepository;
import com.example.taskmanagement.response.ResponseHandler;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides services related to Tag operations.
 * It interacts with the TagRepository and TaskRepository to perform CRUD operations and retrieve data.
 */
@AllArgsConstructor
@Service
public class TagService {

    private TaskService taskService;
    private TagRepository tagRepository;
    private TaskRepository taskRepository;

    /**
     * Creates a new Tag in the database.
     *
     * @param tag A set of Tag objects to be created.
     * @return A ResponseEntity containing the HTTP status code and a message indicating the success or failure of the operation.
     *         If successful, it also includes the created Tag objects.
     */
    @Transactional
    public ResponseEntity<?> createTag(Set<Tag> tag) {

        if (tag.isEmpty()) {
            return ResponseHandler.ResponseBuilder("Please provide at least one tag", HttpStatus.BAD_REQUEST);
        }
        Set<Tag> processedTag = taskService.processedTag(tag);
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
    public TagWithTasksDTO getTagWithTasks(Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag not found with ID: " + tagId));

        List<Long> taskIds = getTaskIdsForTag(tagId);
        List<Task> tasks = taskRepository.findAllById(taskIds);

        return new TagWithTasksDTO(tag.getId(), tag.getName(), tasks);
    }

    /**
     * Retrieves a list of all Tags along with their associated task counts from the database.
     *
     * @return A list of TagDTO objects containing the Tag ID, name, and task count.
     */
    @Transactional
    public List<TagDTO> getTagsWithTaskCounts() {
        List<Tag> tags = getAllTags();
        List<TagDTO> tagWithTaskCounts = new ArrayList<>();

        for (Tag tag : tags) {
            int taskCount = getTaskCountForTag(tag.getId());
            tagWithTaskCounts.add(new TagDTO(tag.getId(), tag.getName(), (long) taskCount));
        }

        return tagWithTaskCounts;
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
    public int getTaskCountForTag(Long tagId) {
        return tagRepository.countTasksByTagId(tagId);
    }

    /**
     * Retrieves the IDs of Tasks associated with a given Tag from the database.
     *
     * @param tagId The ID of the Tag.
     * @return A list of Task IDs associated with the Tag.
     */
    public List<Long> getTaskIdsForTag(Long tagId) {
        return tagRepository.findTaskIdsByTagId(tagId);
    }

}
