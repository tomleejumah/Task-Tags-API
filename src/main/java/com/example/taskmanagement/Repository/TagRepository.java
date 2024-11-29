package com.example.taskmanagement.Repository;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);
//    @Query("SELECT t.name, COUNT(tt.task.id) as taskCount FROM Tag t " +
//            "LEFT JOIN t.tasks tt " +
//            "GROUP BY t.name")
//    Map<String, Long> findAllTagsWithTaskCounts();
//
//    @Query("SELECT t FROM Tag t " +
//            "LEFT JOIN FETCH t.tasks " +
//            "WHERE t.name = :tagName")
//    Optional<Tag> findByNameWithTasks(String tagName);

}
