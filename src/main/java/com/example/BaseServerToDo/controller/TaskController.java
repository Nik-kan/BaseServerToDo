package com.example.BaseServerToDo.controller;

import com.example.BaseServerToDo.dto.CreateTaskDto;
import com.example.BaseServerToDo.dto.GetTaskDto;
import com.example.BaseServerToDo.entity.TaskEntity;
import com.example.BaseServerToDo.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskEntity> createTask(@RequestBody CreateTaskDto createTaskDto){
        return new ResponseEntity<>(taskService.createTask(createTaskDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GetTaskDto>> getAllTasksById(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskService.getAllTasksByUserId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus deleteAllReady(){
        taskService.deleteAllReady();
        return HttpStatus.OK;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> patchTask(@PathVariable Long id, @RequestBody CreateTaskDto createTaskDto) {
        return new ResponseEntity<>(taskService.patchTask(id, createTaskDto), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<TaskEntity> patchTaskStatus (@PathVariable Long id, boolean status){
        return new ResponseEntity<>(taskService.patchTaskStatus(id, status), HttpStatus.OK);
    }
}
