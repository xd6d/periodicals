package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {
    User createUser(String username, String email, String password);

    String getPassword(String email);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    boolean replenish(User user, double amount);

    boolean subscribe(User user, int publicationId);

    List<User> getAllUsers();

    boolean block(User user);

    boolean unblock(User user);
}
