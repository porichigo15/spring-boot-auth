package com.example.assignmentapi.controller;

import com.example.assignmentapi.model.ResponseBodyModel;
import com.example.assignmentapi.model.User;
import com.example.assignmentapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<ResponseBodyModel<User>> register(@RequestBody User user) {
        ResponseBodyModel<User> result = userService.register(user);
        return ResponseEntity.ok(result);
    }
}
