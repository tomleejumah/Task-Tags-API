package com.example.taskmanagement.Controller;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.Model.Tag;
import com.example.taskmanagement.Repository.TaskRepository;
import com.example.taskmanagement.Service.TagService;
import com.example.taskmanagement.Service.TaskService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tags")
@AllArgsConstructor
@RestController
public class TagsController {

    @Autowired
    private TagService tagService;

    @PostMapping("/create/")
    public ResponseEntity<?> createTag(@RequestBody Set<Tag> tag) {
        return tagService.createTag(tag);
    }

//    @GetMapping("/find/all")
//    public List<TagDTO> getAllTags() {
//        return tagService.getTagsWithTaskCount();
//    }


}
