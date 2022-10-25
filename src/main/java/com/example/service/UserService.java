package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {
    User createUser(String username, String email, String password, String confirmPassword);

    String getPassword(String email);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    boolean replenish(User user, double amount);

    boolean replenish(User user, String amount);

    boolean subscribe(User user, int publicationId);

    boolean subscribe(User user, String publicationId);

    List<User> getAllUsers();

    boolean block(User user);

    boolean block(String username);

    boolean unblock(User user);

    boolean unblock(String username);

    User enterAccount(String email, String givenPassword);
}
