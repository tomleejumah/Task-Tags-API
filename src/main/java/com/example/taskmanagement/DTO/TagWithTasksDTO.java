package com.example.taskmanagement.DTO;

import com.example.taskmanagement.Model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagWithTasksDTO {
    private Long tagId;
    private String tagName;
    private List<Task> tasks;
}
