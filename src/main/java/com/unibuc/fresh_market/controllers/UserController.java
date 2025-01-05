package com.unibuc.fresh_market.controllers;

import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.repositories.security.UserRepository;
import com.unibuc.fresh_market.services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
