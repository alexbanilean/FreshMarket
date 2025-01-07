package com.unibuc.fresh_market.services.UserService;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.domain.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Integer id);
    Optional<User> updateUser(Integer id, User user);
    Optional<User> deleteUser(Integer id);

    List<Order> getOrdersByUserId(Integer userId);
    List<Review> getReviewsByUserId(Integer userId);
}
