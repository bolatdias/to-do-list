package com.example.todolist.payload;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TaskPageableResponse {

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_elements")
    private Long totalElements;

    @JsonProperty("tasks")
    private List<TaskResponse> task;
}
