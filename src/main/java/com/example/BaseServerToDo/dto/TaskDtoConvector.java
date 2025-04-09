package com.example.BaseServerToDo.dto;

import com.example.BaseServerToDo.entity.TaskEntity;
import lombok.NonNull;

public class TaskDtoConvector {

    public GetTaskDto convertTaskToDTO(@NonNull TaskEntity taskEntity) {
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
