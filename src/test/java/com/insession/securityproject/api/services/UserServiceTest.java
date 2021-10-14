package com.insession.securityproject.api.services;

import com.insession.securityproject.api.mocks.MockUserRepository;
import com.insession.securityproject.api.mocks.MockUserService;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private IUserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new MockUserRepository());
    }

    @Test
    void someMethod() {
        String username = "bob";
        User user =userService.someMethod(username);
        assertEquals(username, user.getUsername());
    }
}