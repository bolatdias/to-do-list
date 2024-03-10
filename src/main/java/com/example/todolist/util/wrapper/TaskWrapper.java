package com.example.todolist.util.wrapper;

import com.example.todolist.model.Category;
import com.example.todolist.model.Task;
import com.example.todolist.payload.TaskRequest;
import com.example.todolist.payload.TaskResponse;

public class TaskWrapper {

    public static Task convertToModel(TaskRequest taskRequest, Category category) {
        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setCategory(category);
        task.setPriority(taskRequest.getPriority());
        task.setEndAt(taskRequest.getEndAt());
        task.setStartAt(taskRequest.getStartAt());

        return task;
    }

    public static TaskResponse convertToResponse(Task task) {
        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setPriority(task.getPriority());
        taskResponse.setStartAt(task.getStartAt());
        taskResponse.setEndAt(task.getEndAt());
        return taskResponse;
    }

    public static void updateModel(Task task, TaskRequest taskRequest, Category category) {

        task.setTitle(taskRequest.getTitle());
        task.setCategory(category);
        task.setPriority(taskRequest.getPriority());
        task.setEndAt(taskRequest.getEndAt());
        task.setStartAt(taskRequest.getStartAt());

    }
}
