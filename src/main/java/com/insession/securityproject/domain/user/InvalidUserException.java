package com.insession.securityproject.domain.user;

public class InvalidUserException extends Exception {
    public InvalidUserException(String message) {
        super(message);
    }
}
