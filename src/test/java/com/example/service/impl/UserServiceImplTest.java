package com.example.service.impl;

import com.example.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl();
    private User testUser = new User("username91", "email91@email.com");
    private final String testUserPassword = "UsErNaMe91";
    private final String invalidUsernameEmail = "";

    @Test
    void createUser() {
        assertNull(userService.createUser(testUser.getUsername(), testUser.getEmail(), testUserPassword, testUserPassword));
    }

    @Test
    void getPassword() {
        assertEquals(userService.getPassword(testUser.getEmail()), testUserPassword);
        assertNull(userService.getPassword(null));
        assertNull(userService.getPassword(invalidUsernameEmail));
    }

    @Test
    void getUserByEmail() {
        assertEquals(testUser, userService.getUserByEmail(testUser.getEmail()));
        assertNull(userService.getUserByEmail(null));
        assertNull(userService.getUserByEmail(invalidUsernameEmail));
    }

    @Test
    void getUserByUsername() {
        assertEquals(testUser, userService.getUserByUsername(testUser.getUsername()));
        assertNull(userService.getUserByUsername(null));
        assertNull(userService.getUserByUsername(invalidUsernameEmail));
    }

    @Test
    void replenish() {
        testUser = userService.getUserByUsername(testUser.getUsername());
        double replenish = 10;
        assertTrue(userService.replenish(testUser, replenish));
        assertFalse(userService.replenish(null, replenish));
    }

    @Test
    void subscribe() {
        testUser = userService.getUserByUsername(testUser.getUsername());
        assertTrue(userService.subscribe(testUser, 16));
        assertFalse(userService.subscribe(null, 16));
    }

    @Test
    void block() {
        testUser = userService.getUserByUsername(testUser.getUsername());
        assertTrue(userService.block(testUser));
        assertFalse(userService.block((User) null));
    }

    @Test
    void unblock() {
        testUser = userService.getUserByUsername(testUser.getUsername());
        assertTrue(userService.unblock(testUser));
        assertFalse(userService.unblock((User) null));
    }
}