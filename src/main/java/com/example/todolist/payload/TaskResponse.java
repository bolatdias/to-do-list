package com.example.todolist.payload;


import com.example.todolist.model.Priority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private Priority priority;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
