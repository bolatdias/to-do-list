package com.example.todolist.util.wrapper;

import com.example.todolist.model.Category;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.payload.CategoryRequest;
import com.example.todolist.payload.CategoryResponse;
import com.example.todolist.payload.TaskResponse;

import java.util.ArrayList;
import java.util.List;

public class CategoryWrapper {

    public static CategoryResponse convertToResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();

        List<Task> taskSet = category.getTasks();
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : taskSet) {

            TaskResponse taskResponse = TaskWrapper.convertToResponse(task);
            taskResponses.add(taskResponse);
        }

        categoryResponse.setId(category.getId());
        categoryResponse.setTitle(category.getTitle());
        categoryResponse.setTasks(taskResponses);

        return categoryResponse;
    }


    public static Category convertToModel(CategoryRequest categoryRequest, User user) {
        Category category = new Category();
        category.setTitle(categoryRequest.getTitle());
        category.setUser(user);

        return category;
    }

    public static void updateModel(CategoryRequest request, Category category) {
        category.setTitle(request.getTitle());
    }
}
