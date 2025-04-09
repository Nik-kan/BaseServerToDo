package com.example.BaseServerToDo.service;

import com.example.BaseServerToDo.dto.*;
import com.example.BaseServerToDo.entity.TaskEntity;
import com.example.BaseServerToDo.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskDtoConvector taskDtoConvector;

// это мне для наглядности
//    public TaskService(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }

    public TaskEntity createTask(@NonNull CreateTaskDto createTaskDto) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(createTaskDto.getTitle())
                .date(createTaskDto.getDate())
                .userId(createTaskDto.getUserId())
                .taskStatus(false)
                .build();
        return taskRepository.save(taskEntity);
    }

    public List<GetTaskDto> getAllTasksByUserId(@NonNull Long userId) {
        return taskRepository.findAll()
                .stream()
                .filter(TaskEntity -> TaskEntity.getUserId().equals(userId))
                .map(taskDtoConvector::convertTaskToDTO)
                .toList();
    }

    public void deleteTaskById(@NonNull Long id) {
        taskRepository.deleteById(id);
    }

    public void deleteAllReady() {
        List<TaskEntity> completedTasks = taskRepository.findAll().stream()
                .filter(TaskEntity::isTaskStatus)
                .toList();
        taskRepository.deleteAll(completedTasks);
    }

    public TaskEntity patchTask(@NonNull Long id, @NonNull CreateTaskDto createTaskDto) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new NullPointerException("Task not found"));
        taskEntity.setTitle(createTaskDto.getTitle());
        taskEntity.setDate(createTaskDto.getDate());
        return taskRepository.save(taskEntity);
    }

    public TaskEntity patchTaskStatus(@NonNull Long id, boolean status) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> new NullPointerException("Task not found"));
        taskEntity.setTaskStatus(status);
        return taskRepository.save(taskEntity);
    }
}