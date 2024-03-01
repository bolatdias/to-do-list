package com.example.todolist.repository;

import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT t FROM Task t JOIN t.category c WHERE c.user = :user AND t.id = :id")
    Optional<Task> existsByUser(@Param("user") User user, @Param("id") Long id);
}
