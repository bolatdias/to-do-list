package com.example.todolist.service;


import com.example.todolist.exception.AccessDeniedException;
import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.Category;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.payload.CategoryRequest;
import com.example.todolist.payload.CategoryResponse;
import com.example.todolist.payload.TaskRequest;
import com.example.todolist.payload.TaskResponse;
import com.example.todolist.repository.CategoryRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.util.wrapper.CategoryWrapper;
import com.example.todolist.util.wrapper.TaskWrapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }


    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not founded."));

        return TaskWrapper.convertToResponse(task);
    }


    public List<CategoryResponse> getAllTasksByUserId(Long id) {
        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        Set<Category> categoryList = categoryRepository.findByUserId(id);
        for (Category category : categoryList) {
            categoryResponseList.add(CategoryWrapper.convertToResponse(category));
        }
        return categoryResponseList;
    }


    public void createCategory(CategoryRequest categoryRequest, User user) {
        categoryRepository.save(CategoryWrapper.convertToModel(categoryRequest, user));
    }

    public void createTask(TaskRequest taskRequest, User user) {
        Category category = categoryRepository.findById(taskRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not founded."));

        Set<Category> categories = categoryRepository.findByUserId(user.getId());


        if (categories.contains(category)) {
            Task task = TaskWrapper.convertToModel(taskRequest, category);

            taskRepository.save(task);
        } else {
            throw new AccessDeniedException("You don't have access to the specified category");
        }

    }

    public void updateTask(TaskRequest taskRequest, User user, Long id) {
        Category category = categoryRepository.findById(taskRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not founded."));

        Set<Category> categories = categoryRepository.findByUserId(user.getId());

        if (categories.contains(category)) {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not founded."));

            TaskWrapper.updateModel(task, taskRequest, category);

            taskRepository.save(task);
        } else {
            throw new AccessDeniedException("You don't have access to the specified category");
        }
    }

    public void deleteTask(User user, Long id) {
        if (taskRepository.existsById(id)) {
            Task task = taskRepository.existsByUser(user, id).orElseThrow(
                    () -> new AccessDeniedException("You don't have access to the specified task")
            );
            taskRepository.delete(task);
        } else {
            throw new ResourceNotFoundException("Task not Founded");
        }
    }

    public void updateCategory(CategoryRequest categoryRequest, User user, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not founded.")
                );

        Set<Category> categorySet = categoryRepository.findByUserId(user.getId());
        if (categorySet.contains(category)) {
            CategoryWrapper.updateModel(categoryRequest, category);
        } else {
            throw new AccessDeniedException("You don't have access to the specified category");
        }
    }
}


