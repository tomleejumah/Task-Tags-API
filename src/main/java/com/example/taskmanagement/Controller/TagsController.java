package com.example.taskmanagement.Controller;

import com.example.taskmanagement.Model.Tag;
import com.example.taskmanagement.Service.TagService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/tags")
@AllArgsConstructor
@NoArgsConstructor
@RestController
public class TagsController {

    private TagService tagService;

    @PostMapping("/createtag")
    public String createTag(@RequestBody Tag tag){
        // Implement logic to create a new tag
        return "Tag created successfully";
    }

    @GetMapping("/getalltags")
    public List<Tag> getAllTags(){
        // Implement logic to retrieve all tags
        return List.of(new Tag(), new Tag());
    }
}
