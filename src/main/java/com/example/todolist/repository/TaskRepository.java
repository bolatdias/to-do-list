package com.example.todolist.repository;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.payload.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t JOIN t.category c WHERE c.user = :user AND t.id = :id")
    Optional<Task> existsByUser(@Param("user") User user, @Param("id") Long id);

    @Query("SELECT t.priority, COUNT(t) FROM Category c LEFT JOIN c.tasks t LEFT JOIN User u ON " +
            "c.user.id = u.id AND u.id = :userId GROUP BY t.priority")
    List<Object[]> countTasksByPriorityAndUserId(@Param("userId") Long userId);
    @Query("SELECT t FROM Task t JOIN t.category c WHERE c.user = :user " +
            "AND (:#{#filter.title} IS NULL OR t.title LIKE %:#{#filter.title}%) " +
            "AND (:#{#filter.priority} IS NULL OR t.priority = :#{#filter.priority}) " +
            "AND (:#{#filter.categoryId} IS NULL OR c.id = :#{#filter.categoryId})")
    Page<Task> findAllByUser(@Param("user") User user, Pageable pageable, @Param("filter") SearchFilter searchFilter);
}
