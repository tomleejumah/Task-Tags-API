package com.example.taskmanagement.Service;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.Model.Tag;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TagService {
//    private static final Log LOG = LogFactory.getLog(TagService.class);
    @Autowired
    private  TagRepository tagRepository;
    private TaskRepository taskRepository;
    private TaskService taskService;

    @Transactional
    public ResponseEntity<?> createTag(Set<Tag> tag) {

        if (tag.isEmpty()) {
            return ResponseHandler.ResponseBuilder("Please provide at least one tag", HttpStatus.BAD_REQUEST);
        }
        Set<Tag> processedTag = taskService.processedTag(tag);
        return ResponseHandler.ResponseBuilder("Tag Created successfully", HttpStatus.CREATED, processedTag);
    }

//    @Transactional
//    public List<TagDTO> getTagsWithTaskCount() {
//
//        List<TagDTO> tagCounts = taskRepository.findTagsWithTaskCount();
//        for (TagDTO tagCount : tagCounts) {
//            String tagName = tagCount.getName();
//            Long count = tagCount.getTagTaskCount();
//            System.out.println(tagName + ": " + count + " tasks");
//        }
//        return tagCounts;
//    }
//


//        List<TagDTO> results = tagRepository.findTagsWithTaskCount();
//
//        return results.stream()
//                .map(result -> {
//                    TagDTO tagDTO = new TagDTO();
//                    tagDTO.setName((String) result[0]);
//                    tagDTO.setTaskCount((Long) result[1]);
//                    return tagDTO;
//                })
//                .collect(Collectors.toList());
//    }
}
