package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.responses.BaseResponse;
import com.example.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BaseResponse<User>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(new BaseResponse<>(true, userService.findById(id)));
    }

    @GetMapping("/{username}")
    public ResponseEntity<BaseResponse<User>> findByUserName(@PathVariable String username) {
        return ResponseEntity.ok(new BaseResponse<>(true, userService.findByUsername(username)));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<User>> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(new BaseResponse<>(true, userService.createUser(createUserRequest)));
    }
}
