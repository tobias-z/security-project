package com.insession.securityproject.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhitelistTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validateInput() {

        // Test Case 1:
        String name1 = "abc";
        boolean case1 = Whitelist.validateInput(name1);
        assertTrue(case1);

        // Test Case 2:
        String name2 = "<script>abc";
        boolean case2 = Whitelist.validateInput(name2);
        assertFalse(case2);

    }

    @Test
    void validateEmail() {

        // Test Case 1:
        String email1 = "abc@adc.dk";
        boolean case1 = Whitelist.validateEmail(email1);
        assertTrue(case1);

        // Test Case 2:
        String email2 = "abcadc.dk";
        boolean case2 = Whitelist.validateEmail(email2);
        assertFalse(case2);

    }

    @Test
    void validateImageFile() {

        // Test Case 1:
        String check1 = "abc.png";
        boolean case1 = Whitelist.validateImageFile(check1);
        assertTrue(case1);

        // Test Case 2:
        String check2 = "im.jpg";
        boolean case2 = Whitelist.validateImageFile(check2);
        assertTrue(case2);

        // Test Case 3:
        String check3 = ".gif";
        boolean case3 = Whitelist.validateImageFile(check3);
        assertFalse(case3);

        // Test Case 4:
        String check4 = "malicious.jpg.html";
        boolean case4 = Whitelist.validateImageFile(check4);
        assertFalse(case4);

    }

}