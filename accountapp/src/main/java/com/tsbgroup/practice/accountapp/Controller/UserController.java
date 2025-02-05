package com.tsbgroup.practice.accountapp.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsbgroup.practice.accountapp.Model.User;
import com.tsbgroup.practice.accountapp.Service.UserService;

import jakarta.annotation.PostConstruct;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostConstruct
    public void init()  {
        System.out.println("UserController is running...");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        System.out.println("Looking for user with ID: " + userId);
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok) // If user exists, return 200 OK
            .orElseGet(() -> {
                System.out.println("User with ID " + userId + " not found!");
                return ResponseEntity.notFound().build();
            });
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        System.out.println("Creating user: " + user.getUserId());
        return ResponseEntity.ok(userService.saveUser(user));
    }
    
    
}
