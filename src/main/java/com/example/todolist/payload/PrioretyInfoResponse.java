package com.example.todolist.payload;


import com.example.todolist.model.Priority;
import lombok.Data;

@Data
public class PrioretyInfoResponse {
    private Priority priority;
    private Long count;
}
