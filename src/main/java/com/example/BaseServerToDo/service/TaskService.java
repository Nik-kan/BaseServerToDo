package com.example.BaseServerToDo.service;

import com.example.BaseServerToDo.dto.*;
import com.example.BaseServerToDo.entity.TaskEntity;
import com.example.BaseServerToDo.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskDtoConvertor taskDtoConvector;


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

    public Page<GetTaskDto> getPaginatedTasks(int pageNo, int pageSize, @NonNull Long userId) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<TaskEntity> taskPage = taskRepository.findAll(pageable);
        List<GetTaskDto> getTaskDtoList = taskPage.getContent()
                .stream()
                .filter(TaskEntity -> TaskEntity.getUserId().equals(userId))
                .map(taskDtoConvector::convertTaskToDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(getTaskDtoList, pageable, taskPage.getTotalElements());
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