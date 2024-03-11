package com.example.todolist.payload;


import com.example.todolist.model.Priority;
import lombok.Data;

@Data
public class SearchFilter {
    private String title;
    private Priority priority;
    private Long categoryId;
}
