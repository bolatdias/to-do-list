package com.example.todolist.controller;


import com.example.todolist.model.Category;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.payload.*;
import com.example.todolist.security.CurrentUser;
import com.example.todolist.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final TaskService taskService;

    public UserController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(
            @CurrentUser User user
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryResponse>> getAllTask(
            @CurrentUser User currentUser
    ) {

        List<CategoryResponse> categoryResponseList = taskService.getAllTasksByUserId(currentUser.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseList);
    }

    @GetMapping("task/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse taskResponse = taskService.getTaskById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskResponse);
    }

    @PostMapping("/category")
    public ResponseEntity<ApiResponse> createCategory(
            @RequestBody CategoryRequest categoryRequest,
            @CurrentUser User user

    ) {
        taskService.saveCategory(categoryRequest, user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Category successfully created."));
    }

    @PostMapping("/task")
    public ResponseEntity<ApiResponse> createTask(
            @RequestBody TaskRequest taskRequest,
            @CurrentUser User user
    ) {
        taskService.createTask(taskRequest, user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Task successfully created."));
    }

    @PatchMapping("/task/{id}")
    public ResponseEntity<ApiResponse> update(
            @RequestBody TaskRequest taskRequest,
            @CurrentUser User user,
            @PathVariable Long id
    ) {
        taskService.updateTask(taskRequest, user, id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Task successfully updated."));
    }


    @DeleteMapping("task/{id}")
    public ResponseEntity<ApiResponse> deleteTask(
            @CurrentUser User user,
            @PathVariable Long id
    ) {
        taskService.deleteTask(user, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse(true, "Task successfully deleted."));
    }


}
