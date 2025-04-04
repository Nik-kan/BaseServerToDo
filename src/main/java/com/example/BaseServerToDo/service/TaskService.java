package com.example.BaseServerToDo.service;

import com.example.BaseServerToDo.dto.CreateTaskDto;
import com.example.BaseServerToDo.dto.GetTaskDto;
import com.example.BaseServerToDo.entity.TaskEntity;
import com.example.BaseServerToDo.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public TaskEntity createTask(@NonNull CreateTaskDto createTaskDto) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(createTaskDto.getTitle())
                .date(createTaskDto.getDate())
                .userId(createTaskDto.getUserId())
                .taskStatus(false)
                .build();
        return taskRepository.save(taskEntity);
    }

    // Прошлый вариант
    //    public TaskEntity createTask(CreateTaskDto createTaskDto) {
//        TaskEntity taskEntity = new TaskEntity();
//        taskEntity.setTitle(createTaskDto.getTitle());
//        taskEntity.setDate(createTaskDto.getDate());
//        taskEntity.setUserId(createTaskDto.getUserId());
//        taskRepository.save(taskEntity);

    public List<GetTaskDto> getAllTasksByUserId(@NonNull Long userId) {
        return taskRepository.findById(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Прошлый вариант
//    public List<GetTaskDto> getAllTasksById() {
//        return taskRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//    }   // как сделать, чтобы пользователь получал только свои задачи

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

    public GetTaskDto convertToDTO(@NonNull TaskEntity taskEntity) {
        GetTaskDto getTaskDto = new GetTaskDto();
        getTaskDto.setId(taskEntity.getId());
        getTaskDto.setTitle(taskEntity.getTitle());
        getTaskDto.setDate(taskEntity.getDate());
        getTaskDto.setUserId(taskEntity.getUserId());
        getTaskDto.setTaskStatus(taskEntity.isTaskStatus());
        getTaskDto.setTaskStatus(taskEntity.isTaskStatus());
        return getTaskDto;
    }
}