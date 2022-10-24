package com.example.service.impl;

import com.example.dao.PublicationRepository;
import com.example.dao.UserRepository;
import com.example.dao.impl.PublicationRepositoryImpl;
import com.example.dao.impl.UserRepositoryImpl;
import com.example.entity.Publication;
import com.example.entity.User;
import com.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository ur = new UserRepositoryImpl();
    private final PublicationRepository pr = new PublicationRepositoryImpl();

    @Override
    public User createUser(String username, String email, String password) {
        User user = new User(username, email);
        boolean isCreated = ur.insertUser(user, password);
        if (isCreated)
            return ur.findUserByUsername(username);
        else
            return null;
    }

    @Override
    public String getPassword(String email) {
        return ur.getPasswordByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return ur.findUserByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return ur.findUserByUsername(username);
    }

    @Override
    public boolean replenish(User user, double amount) {
        if (user == null)
            return false;
        boolean success = ur.updateUserBalance(user, amount);
        if (success)
            user.setBalance(user.getBalance() + amount);
        return success;
    }

    @Override
    public boolean subscribe(User user, int publicationId) {
        if (user == null)
            return false;
        Publication publication = pr.findById(publicationId);
        if (publication == null)
            return false;
        if (user.getBalance() - publication.getPrice() < 0)
            return false;
        boolean success = ur.subscribe(user, publicationId);
        if (success) {
            user.getPublications().add(publication);
            ur.updateUserBalance(user, -publication.getPrice());
        }
        return success;
    }

    @Override
    public List<User> getAllUsers() {
        return ur.findAll();
    }

    @Override
    public boolean block(User user) {
        if (user == null)
            return false;
        boolean success = ur.block(user);
        if (success)
            user.setBlocked(true);
        return success;
    }

    @Override
    public boolean unblock(User user) {
        if (user == null)
            return false;
        boolean success = ur.unblock(user);
        if (success)
            user.setBlocked(false);
        return success;
    }
}
