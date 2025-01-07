package com.unibuc.fresh_market.controller;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.service.UserService.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Users controller")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get a user by id")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        Integer id = Integer.parseInt(userId);
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update a user")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, BindingResult bindingResult, @PathVariable String userId) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors found!");
        }

        Integer id = Integer.parseInt(userId);
        Optional<User> updatedUser = userService.updateUser(id, user);
        if (updatedUser.isPresent()) {
            return ResponseEntity.ok(updatedUser.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        Integer id = Integer.parseInt(userId);
        Optional<User> deletedUser = userService.deleteUser(id);
        if (deletedUser.isPresent()) {
            return ResponseEntity.ok(deletedUser.get());
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get orders for a user")
    @GetMapping("/{userId}/orders")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable String userId) {
        Integer id = Integer.parseInt(userId);
        List<Order> orders = userService.getOrdersByUserId(id);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get reviews for a user")
    @GetMapping("/{userId}/reviews")
    public ResponseEntity<?> getReviewsByUserId(@PathVariable String userId) {
        Integer id = Integer.parseInt(userId);
        List<Review> reviews = userService.getReviewsByUserId(id);
        return ResponseEntity.ok(reviews);
    }

}
