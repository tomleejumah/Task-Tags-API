package com.example.taskmanagement.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Tag {
    @Schema(description = "Unique identifier for the tag", example = "1")
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

}
