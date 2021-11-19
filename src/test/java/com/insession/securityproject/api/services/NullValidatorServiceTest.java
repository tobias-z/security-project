package com.insession.securityproject.api.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NullValidatorServiceTest {

    @Test
    @DisplayName("validateField should not throw when given correct values")
    void validateFieldShouldNotThrowWhenGivenCorrectValues() throws Exception {
        String message = "dsadadada";
        assertDoesNotThrow(() -> NullValidatorService.validateField(message, "message", Exception.class));
    }

    @Test
    @DisplayName("validateField should throw when given empty string")
    void validateFieldShouldThrowWhenGivenIncorrectValues() throws Exception {
        String message = "";
        assertThrows(Exception.class, () -> NullValidatorService.validateField(message, "message", Exception.class));
    }

    @Test
    @DisplayName("validateField should throw when given null")
    void validateFieldShouldThrowWhenGivenNull() throws Exception {
        String message = null;
        assertThrows(Exception.class, () -> NullValidatorService.validateField(message, "message", Exception.class));
    }
}