package com.example.user_service.controller;

import com.example.user_service.dto.UserResponse;
import com.example.user_service.entity.Users;
import com.example.user_service.service.UsersServiceImpl;
import com.example.user_service.service.UsersServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersServices service;

    @GetMapping
    public ResponseEntity<Page<Users>>
    getAllUsers(@RequestParam(defaultValue = "1") int pageNo,
                @RequestParam(defaultValue = "2") int pageSize,
                @RequestParam(defaultValue = "userName") String sortBy,
                @RequestParam(defaultValue = "ASC") String sortDir){

        Page<Users> getAll=service.getAllUsers(pageNo,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(getAll);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Users> deleteByUserId(@PathVariable Long id){
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Users> addUsers(@RequestBody Users users){
        Users addUser=service.addUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(addUser);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        Users byId=service.getUserById(id);
        return ResponseEntity.ok(byId);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findByUserId(@PathVariable Long userId){
        UserResponse byUserId=service.getByUserId(userId);
        return ResponseEntity.ok(byUserId);

    }
}
