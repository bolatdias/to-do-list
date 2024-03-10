package com.example.todolist.util.wrapper;

import com.example.todolist.model.Category;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.payload.CategoryRequest;
import com.example.todolist.payload.CategoryFullResponse;
import com.example.todolist.payload.CategoryResponse;
import com.example.todolist.payload.TaskResponse;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryFullResponse convertToResponse(Category category) {
        CategoryFullResponse categoryFullResponse = new CategoryFullResponse();

        List<Task> taskSet = category.getTasks();
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : taskSet) {

            TaskResponse taskResponse = TaskMapper.convertToResponse(task);
            taskResponses.add(taskResponse);
        }

        categoryFullResponse.setId(category.getId());
        categoryFullResponse.setTitle(category.getTitle());
        categoryFullResponse.setTasks(taskResponses);

        return categoryFullResponse;
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

    public static CategoryResponse convertToModel(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setTitle(category.getTitle());
        categoryResponse.setId(category.getId());
        return categoryResponse;
    }
}
