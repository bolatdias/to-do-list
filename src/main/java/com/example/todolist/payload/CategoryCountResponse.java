package com.example.todolist.payload;

import lombok.Data;

@Data
public class CategoryCountResponse {
    private Long id;
    private String title;
    private Long count;
}
