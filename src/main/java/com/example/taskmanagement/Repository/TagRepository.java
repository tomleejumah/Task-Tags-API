package com.example.taskmanagement.Repository;

import com.example.taskmanagement.DTO.TagDTO;
import com.example.taskmanagement.Model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);


    @Query(value = "SELECT COUNT(*) FROM task_tag WHERE tag_id = :tagId", nativeQuery = true)
    int countTasksByTagId(@Param("tagId") Long tagId);

    // Native query to get task IDs for a specific tag
    @Query(value = "SELECT task_id FROM task_tag WHERE tag_id = :tagId", nativeQuery = true)
    List<Long> findTaskIdsByTagId(@Param("tagId") Long tagId);

    // Comprehensive method to get tag usage details
    @Query(value = "SELECT " +
            "t.id AS tag_id, " +
            "t.name AS tag_name, " +
            "COUNT(tt.task_id) AS task_count, " +
            "GROUP_CONCAT(tt.task_id) AS task_ids " +
            "FROM tags t " +
            "LEFT JOIN task_tag tt ON t.id = tt.tag_id " +
            "GROUP BY t.id, t.name", nativeQuery = true)
    List<Object[]> getTagUsageDetails();
}
