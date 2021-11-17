package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.repositories.base.ActionException;

public class UserNotFoundException extends ActionException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
