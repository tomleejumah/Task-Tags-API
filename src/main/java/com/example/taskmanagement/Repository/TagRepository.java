package com.example.taskmanagement.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.taskmanagement.Model.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Tag entity. Provides methods for interacting with
 * the database.
 *
 * @author tommlyjumah
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

  /**
   * Finds a Tag by its name.
   *
   * @param name The name of the Tag to find.
   * @return An Optional containing the Tag if found, otherwise an empty Optional.
   */
  Optional<Tag> findByName(String name);

  /**
   * Counts the number of tasks associated with a specific Tag.
   *
   * @param tagId The ID of the Tag.
   * @return The count of tasks associated with the Tag.
   */
  @Query(value = "SELECT COUNT(*) FROM task_tag WHERE tag_id = :tagId", nativeQuery = true)
  int countTasksByTagId(@Param("tagId") Long tagId);

  /**
   * Retrieves the IDs of tasks associated with a specific Tag.
   *
   * @param tagId The ID of the Tag.
   * @return A List of task IDs associated with the Tag.
   */
  @Query(value = "SELECT task_id FROM task_tag WHERE tag_id = :tagId", nativeQuery = true)
  List<Long> findTaskIdsByTagId(@Param("tagId") Long tagId);

  @Query("SELECT t FROM Tag t WHERE t.dateCreated = :dateCreated")
  List<Tag> findTagsByDateCreated(@Param("dateCreated") LocalDate dateCreated);
}
