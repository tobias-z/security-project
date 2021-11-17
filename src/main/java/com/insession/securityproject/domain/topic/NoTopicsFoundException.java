package com.insession.securityproject.domain.topic;

import com.insession.securityproject.infrastructure.repositories.base.ActionException;

public class NoTopicsFoundException extends ActionException {
    public NoTopicsFoundException(String message) {
        super(message);
    }
}
