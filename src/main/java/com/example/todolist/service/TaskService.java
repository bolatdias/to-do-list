package com.example.todolist.service;


import com.example.todolist.exception.AccessDeniedException;
import com.example.todolist.exception.ResourceNotFoundException;
import com.example.todolist.model.Category;
import com.example.todolist.model.Priority;
import com.example.todolist.model.Task;
import com.example.todolist.model.User;
import com.example.todolist.payload.*;
import com.example.todolist.repository.CategoryRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.util.wrapper.CategoryMapper;
import com.example.todolist.util.wrapper.TaskMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return TaskMapper.convertToResponse(task, task.getCategory());
    }


    public TaskPageableResponse getAllTasksByUserId(User user, int page, int pageSize, SearchFilter searchFilter) {
        TaskPageableResponse taskPageableResponse = new TaskPageableResponse();
        List<TaskResponse> taskResponses = new ArrayList<>();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Task> taskPage = taskRepository.findAllByUser(user, pageable, searchFilter);
        List<Task> taskList = taskPage.getContent();

        for (Task task : taskList) {
            TaskResponse taskResponse = TaskMapper.convertToResponse(task, task.getCategory());
            taskResponses.add(taskResponse);
        }

        taskPageableResponse.setTotalPages(taskPage.getTotalPages());
        taskPageableResponse.setTotalElements(taskPage.getTotalElements());
        taskPageableResponse.setTask(taskResponses);

        return taskPageableResponse;
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
            categoryResponseList.add(CategoryMapper.convertToResponse(c));
        }
        return categoryResponseList;
    }

    public List<CategoryCountResponse> getCategoriesCount(User user) {
        List<CategoryCountResponse> categoryCountResponseList = new ArrayList<>();
        List<Object[]> objectsList = categoryRepository.findByUserIdWithCount(user.getId());

        for (Object[] objectArray : objectsList) {
            Category category = (Category) objectArray[0];
            Long count = (Long) objectArray[1];

            CategoryCountResponse categoryCountResponse = new CategoryCountResponse();
            categoryCountResponse.setId(category.getId());
            categoryCountResponse.setTitle(category.getTitle());
            categoryCountResponse.setCount(count);

            categoryCountResponseList.add(categoryCountResponse);
        }

        return categoryCountResponseList;
    }


    public List<PrioretyInfoResponse> getPrioretyInfo(User user) {
        List<PrioretyInfoResponse> prioretyInfoResponseList = new ArrayList<>();

        List<Object[]> objectList = taskRepository.countTasksByPriorityAndUserId(user.getId());

        for (Object[] objects : objectList) {
            Priority priority = (Priority) objects[0];
            Long count = (Long) objects[1];

            PrioretyInfoResponse prioretyInfoResponse = new PrioretyInfoResponse();
            prioretyInfoResponse.setCount(count);
            prioretyInfoResponse.setPriority(priority);

            prioretyInfoResponseList.add(prioretyInfoResponse);
        }

        return prioretyInfoResponseList;
    }
}


