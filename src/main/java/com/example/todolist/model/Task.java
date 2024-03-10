package com.example.todolist.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Enumerated(EnumType.STRING)
    private Priority priority;


    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
