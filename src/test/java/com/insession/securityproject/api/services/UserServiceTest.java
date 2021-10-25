package com.insession.securityproject.api.services;

import com.insession.securityproject.api.mocks.MockUserRepository;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private IUserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new MockUserRepository());
    }


    @Test
    @DisplayName("yay")
    void yay() throws Exception {
        assertTrue(true);
    }

    @Test
    void sendMail() {
        User user = new User("bob", UserRole.USER, "jensgelbek@gmail.com");
        userService.sendPinMail(user);
    }

}