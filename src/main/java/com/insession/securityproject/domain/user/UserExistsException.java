package com.insession.securityproject.domain.user;

public class UserExistsException extends Exception {
    public UserExistsException(String message) {
        super(message);
    }
}
