package com.unibuc.fresh_market.services.UserService;

import com.unibuc.fresh_market.domain.Order;
import com.unibuc.fresh_market.domain.Review;
import com.unibuc.fresh_market.domain.security.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Integer id);
    User updateUser(Integer id, User user);
    void deleteUser(Integer id);

    List<Order> getOrdersByUserId(Integer userId);
    List<Review> getReviewsByUserId(Integer userId);
}
