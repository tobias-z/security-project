package com.insession.securityproject.api.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthPinCodeServiceTest {
    private static AuthPinCodeService authTokenService;

    @BeforeEach
    void setUp() {
        authTokenService = AuthPinCodeService.getInstance();
    }

    @Test
    @DisplayName("auth token works")
    void authTokenWorks() throws Exception {
        String username = "bob";
        Integer token = authTokenService.getNewToken(username);
        assertNotNull(token);
        assertTrue(authTokenService.isValidToken(username, token));
    }
}