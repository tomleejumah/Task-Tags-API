package com.example.taskmanagement.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;
    private String name;
    private Long tagTaskCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;
}
