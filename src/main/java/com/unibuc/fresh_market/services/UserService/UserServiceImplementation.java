package com.unibuc.fresh_market.services.UserService;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.domain.security.User;
import com.unibuc.fresh_market.repositories.security.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(Integer id, User user) {
        User userToUpdate = userRepository.findById(id).orElse(null);

        if (userToUpdate != null) {
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setPassword(user.getPassword());
            userToUpdate.setRoles(user.getRoles());
            userToUpdate.setFarm(user.getFarm());
            userToUpdate.setOrders(user.getOrders());
            userToUpdate.setReviews(user.getReviews());

             User updatedUser = userRepository.save(userToUpdate);
             return Optional.of(updatedUser);
        }

        return Optional.empty();
    }

    public Optional<User> deleteUser(Integer id) {
        User deletedUser = userRepository.findById(id).orElse(null);
        if (deletedUser != null) {
            userRepository.delete(deletedUser);
            return Optional.of(deletedUser);
        }

        return Optional.empty();
    }

    public List<Order> getOrdersByUserId(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            return user.getOrders();
        }

        return new ArrayList<>();
    }

    public List<Review> getReviewsByUserId(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            return user.getReviews();
        }

        return new ArrayList<>();
    }
}
