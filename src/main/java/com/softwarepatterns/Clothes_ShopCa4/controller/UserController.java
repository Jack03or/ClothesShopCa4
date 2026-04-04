package com.softwarepatterns.Clothes_ShopCa4.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softwarepatterns.Clothes_ShopCa4.model.User;
import com.softwarepatterns.Clothes_ShopCa4.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {

        if (userService.usernameExists(user.getUsername())) {
            return "Username already taken.";
        }

        if (userService.emailExists(user.getEmail())) {
            return "Email already in use.";
        }

        if (user.getRole() == null) {
            user.setRole("CUSTOMER");
        }

        if (!user.getRole().equalsIgnoreCase("ADMIN") && !user.getRole().equalsIgnoreCase("CUSTOMER")) {
            return "Role must be ADMIN or CUSTOMER.";
        }

        user.setRole(user.getRole().toUpperCase());

        userService.saveUser(user);
        return "User " + user.getUsername() + " registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody User loginUser) {
        User user = userService.findByUsername(loginUser.getUsername());

        if (user == null || !user.getPassword().equals(loginUser.getPassword())) {
            return "Invalid username or password.";
        }

        if (loginUser.getRole() != null && !loginUser.getRole().isBlank()
                && !user.getRole().equalsIgnoreCase(loginUser.getRole())) {
            return "Invalid role for this login.";
        }

        return "Login successful! Welcome " + user.getUsername() + " (" + user.getRole() + ")";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
