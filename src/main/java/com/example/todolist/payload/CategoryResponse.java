package com.example.todolist.payload;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String title;
    private List<TaskResponse> tasks;
}
