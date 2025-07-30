package com.example.taskmanagement.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
  @Schema(description = "Unique identifier for the task", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Schema(description = "Title of the task", required = true, example = "Complete project report")
  private String tittle;
  @Schema(description = "Completion status of the task", required = true, example = "false")
  private boolean completed;

  @Schema(description = "Due date of the task", example = "2024-12-31")
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dueDate;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "task_tag", joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
  private Set<Tag> tags = new HashSet<>();
}
