package com.example.assignmentapi.controller;

import com.example.assignmentapi.model.User;
import com.example.assignmentapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable("email") String email) {
        User result = userService.getByEmail(email);
        return ResponseEntity.ok(result);
    }
}
