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
    public User createUser(String username, String email, String password, String confirmPassword) {
        if (username == null)
            throw new IllegalArgumentException("Enter username.");
        if (email == null)
            throw new IllegalArgumentException("Enter email.");
        if (password == null)
            throw new IllegalArgumentException("Enter password.");
        if (confirmPassword == null)
            throw new IllegalArgumentException("Enter confirm password.");
        if (!password.equals(confirmPassword))
            throw new IllegalArgumentException("Passwords are not the same.");
        if (getUserByUsername(username) != null)
            throw new IllegalArgumentException("User with the given username already exists.");
        if (getUserByEmail(email) != null)
            throw new IllegalArgumentException("This email is already registered.");
        boolean isCreated = ur.insertUser(new User(username, email), password);
        if (isCreated)
            return ur.findUserByUsername(username);
        else
            throw new RuntimeException("Something went wrong. Please try again later.");
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
        boolean success = ur.updateUserBalance(user, amount);
        if (success)
            user.setBalance(user.getBalance() + amount);
        else
            throw new RuntimeException("Something went wrong. Please try again later.");
        return true;
    }

    @Override
    public boolean replenish(User user, String amount) {
        if (amount == null || !amount.matches("^[+]?[\\d]+$"))
            throw new IllegalArgumentException("Enter amount.");
        return replenish(user, Double.parseDouble(amount));
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
    public boolean subscribe(User user, String publicationId) {
        if (publicationId != null && publicationId.matches("^[+-]?[\\d]+$"))
            return subscribe(user, Integer.parseInt(publicationId));
        return false;
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
    public boolean block(String username) {
        if (username != null)
            return block(getUserByUsername(username));
        return false;
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

    @Override
    public boolean unblock(String username) {
        if (username != null)
            return unblock(getUserByUsername(username));
        return false;
    }

    @Override
    public User enterAccount(String email, String givenPassword) {
        String actualPassword = getPassword(email);
        if (email == null)
            throw new IllegalArgumentException("Enter email.");
        if (givenPassword == null)
            throw new IllegalArgumentException("Enter password.");
        if (actualPassword == null)
            throw new IllegalArgumentException("User with the given email does not exist.");
        if (givenPassword.equals(actualPassword)) {
            return getUserByEmail(email);
        }
        else
            throw new IllegalArgumentException("Incorrect password.");

    }
}
