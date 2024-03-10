package com.example.todolist.service;


import com.example.todolist.exception.AccessDeniedException;
import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.Category;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.payload.*;
import com.example.todolist.repository.CategoryRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.util.wrapper.CategoryMapper;
import com.example.todolist.util.wrapper.TaskMapper;
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

        return TaskMapper.convertToResponse(task);
    }


    public List<CategoryFullResponse> getAllTasksByUserId(Long id) {
        List<CategoryFullResponse> categoryFullResponseList = new ArrayList<>();

        Set<Category> categoryList = categoryRepository.findByUserId(id);
        for (Category category : categoryList) {
            categoryFullResponseList.add(CategoryMapper.convertToResponse(category));
        }
        return categoryFullResponseList;
    }


    public void createCategory(CategoryRequest categoryRequest, User user) {
        categoryRepository.save(CategoryMapper.convertToModel(categoryRequest, user));
    }

    public void createTask(TaskRequest taskRequest, User user) {
        Category category = categoryRepository.findById(taskRequest.getCategory_id())
                .orElseThrow(() -> new ResourceNotFoundException("Category not founded."));

        Set<Category> categories = categoryRepository.findByUserId(user.getId());


        if (categories.contains(category)) {
            Task task = TaskMapper.convertToModel(taskRequest, category);

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

            TaskMapper.updateModel(task, taskRequest, category);

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
            CategoryMapper.updateModel(categoryRequest, category);
        } else {
            throw new AccessDeniedException("You don't have access to the specified category");
        }
    }

    public List<CategoryResponse> getCategories(User user) {
        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        Set<Category> categoryList = categoryRepository.findByUserId(user.getId());
        for (Category c : categoryList) {
            categoryResponseList.add(CategoryMapper.convertToModel(c));
        }
        return categoryResponseList;
    }

}


