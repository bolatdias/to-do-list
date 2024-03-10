package com.example.todolist.payload;


import com.example.todolist.model.Priority;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    private String title;
    private Long category_id;
    private Priority priority;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
