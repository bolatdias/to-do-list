package com.example.todolist.payload;


import lombok.Data;

import java.util.List;

@Data
public class CategoryFullResponse {
    private Long id;
    private String title;
    private List<TaskResponse> tasks;
}
