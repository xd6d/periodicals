package com.example.dao;


import com.example.entity.User;

import java.util.List;

public interface UserRepository {
    User findUserByUsername(String username);

    User findUserByEmail(String email);

    boolean insertUser(User entity, String password);

    String getPasswordByEmail(String email);

    boolean updateUserBalance(User user, double amount);

    boolean subscribe(User user, int publicationId);

    List<User> findAll();

    boolean block(User user);

    boolean unblock(User user);

}
