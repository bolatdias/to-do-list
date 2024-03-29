package com.example.todolist.repository;


import com.example.todolist.model.Category;
import com.example.todolist.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByUserId(Long userId, Pageable pageable);

    Set<Category> findByUserId(Long userId);

    @Query("SELECT c, COUNT(t) FROM Category c LEFT JOIN c.tasks t LEFT JOIN User u ON " +
            "c.user.id = u.id WHERE c.user.id = :userId GROUP BY c")
    List<Object[]> findByUserIdWithCount(@Param("userId") Long userId);

}