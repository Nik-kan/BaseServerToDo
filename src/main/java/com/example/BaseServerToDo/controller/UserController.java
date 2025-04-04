package com.example.BaseServerToDo.controller;

import com.example.BaseServerToDo.dto.GetUserDto;
import com.example.BaseServerToDo.dto.ReplaceUserDto;
import com.example.BaseServerToDo.entity.UserEntity;
import com.example.BaseServerToDo.service.UserService;
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
@RequestMapping("/user_data")
public class UserController {

    @Autowired
    private UserService userService;

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

    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return HttpStatus.OK;
    }

    // Прошлые варианты методов

//    @PostMapping
//    public void replaceUser(@RequestBody ReplaceUserDto replaceUserDto) {
//        userService.replaceUser(replaceUserDto);
//    }

//    @GetMapping
//    public List<GetUserDto> getAllUsers(){
//        return userService.getAllUsers();
//    }

//    @GetMapping("/{id}")
//    public GetUserDto getUserById(@PathVariable("id") Long id){
//        return userService.getUserById(id);
//    }

//    @DeleteMapping("/{id}")
//    public void deleteUserById(@PathVariable("id") Long id){
//        userService.deleteUserById(id);
//    }
}
