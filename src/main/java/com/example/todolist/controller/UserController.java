package com.example.todolist.controller;


import com.example.todolist.model.Priority;
import com.example.todolist.model.User;
import com.example.todolist.payload.*;
import com.example.todolist.security.CurrentUser;
import com.example.todolist.service.ImageService;
import com.example.todolist.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final ImageService imageService;
    private final TaskService taskService;

    public UserController(ImageService imageService, TaskService taskService) {
        this.imageService = imageService;
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
    public ResponseEntity<TaskPageableResponse> getAllTask(
            @CurrentUser User currentUser,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Long categoryId
    ) {

        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setPriority(priority);
        searchFilter.setTitle(title);
        searchFilter.setCategoryId(categoryId);

        TaskPageableResponse categoryFullResponseList = taskService.getAllTasksByUserId(currentUser, page, size, searchFilter);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryFullResponseList);
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
        taskService.createCategory(categoryRequest, user);
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

    @PutMapping("/task/{id}")
    public ResponseEntity<ApiResponse> updateTask(
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


    @PutMapping("/category/{id}")
    public ResponseEntity<ApiResponse> updateCategory(
            @RequestBody CategoryRequest categoryRequest,
            @CurrentUser User user,
            @PathVariable Long id
    ) {
        taskService.updateCategory(categoryRequest, user, id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Task successfully updated."));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategories(
            @CurrentUser User user
    ) {
        List<CategoryResponse> categoryResponseList = taskService.getCategories(user);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryResponseList);
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse> uploadImage(
            @RequestParam("image") MultipartFile file,
            @CurrentUser User user
    ) {
        try {
            imageService.uploadImage(file, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(true, "Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @GetMapping("/image")
    public ResponseEntity<?> getImage(
            @CurrentUser User user
    ) throws IOException {

        byte[] imageData = imageService.getImage(user.getImage().getId());

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}

