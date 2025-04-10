package com.example.BaseServerToDo.controller;

import com.example.BaseServerToDo.dto.GetUserDto;
import com.example.BaseServerToDo.dto.ReplaceUserDto;
import com.example.BaseServerToDo.entity.UserEntity;
import com.example.BaseServerToDo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user_data")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> replaceUser(@RequestBody ReplaceUserDto replaceUserDto) {
        return new ResponseEntity<>(userService.replaceUser(replaceUserDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping
    public Page<GetUserDto> getPaginatedUsers(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return userService.getPaginatedUsers(page, size);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }
}