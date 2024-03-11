package com.example.todolist.payload;


import com.example.todolist.model.Priority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private Priority priority;
    private String description;

    @JsonProperty("category")
    private CategoryResponse categoryResponse;

    @JsonProperty("start_at")
    private LocalDateTime startAt;
    @JsonProperty("end_at")
    private LocalDateTime endAt;
}
