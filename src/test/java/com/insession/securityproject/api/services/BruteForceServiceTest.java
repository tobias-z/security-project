package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.BruteForceException;
import com.insession.securityproject.infrastructure.cache.Redis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceServiceTest {
    private BruteForceService bruteForceService;

    private final String ip = "127.0.0.111";
    private final String u1 = "user1";
    private final String u2 = "user2";
    private final String u3 = "user3";
    private final String u4 = "user4";
    private final String u5 = "user5";

    @BeforeEach
    void setUp() {
        Redis.getConnection()
                .removeAll()
                .close();
        bruteForceService = new BruteForceService(10, 3);
    }

    @Test
    @DisplayName("handleBruteForce will not throw on 3 user tries")
    void handleBruteForceWillNotThrowOn3UserTries() throws Exception {
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
    }

    @Test
    @DisplayName("handleBruteForce will not throw when given 4 attepts on different users")
    void handleBruteForceWillNotThrowWhenGiven4AtteptsOnDifferentUsers() throws Exception {
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));
    }

    @Test
    @DisplayName("handleBruteForce will throw on four attempts")
    void handleBruteForceWillThrowOnFourAttempts() throws Exception {
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertThrows(BruteForceException.class, () -> bruteForceService.handleBruteForce(ip, u1));
    }

    @Test
    @DisplayName("handleBruteForce will keep throwing when 4 attempts has been done to different users")
    void handleBruteForceWillKeepThrowingWhen4AttemptsHasBeenDoneToDifferentUsers() throws Exception {
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertThrows(BruteForceException.class, () -> bruteForceService.handleBruteForce(ip, u1));

        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));
        assertThrows(BruteForceException.class, () -> bruteForceService.handleBruteForce(ip, u2));

        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u3));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u3));
        assertThrows(BruteForceException.class, () -> bruteForceService.handleBruteForce(ip, u3));

        // first attempt at u4
        assertThrows(BruteForceException.class, () -> bruteForceService.handleBruteForce(ip, u4));
    }

    @Test
    @DisplayName("handleBruteForce will keep throwing when maxAttempts has been exceeded")
    void handleBruteForceWillKeepThrowingWhenMaxAttemptsHasBeenExceeded() throws Exception {
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u1));

        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u2));

        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u3));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u3));

        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u4));
        assertDoesNotThrow(() -> bruteForceService.handleBruteForce(ip, u4));

        assertThrows(BruteForceException.class, () -> bruteForceService.handleBruteForce(ip, u5));
        assertThrows(BruteForceException.class, () -> bruteForceService.handleBruteForce(ip, u5));
    }

}