package com.insession.securityproject.domain.comment;

import com.insession.securityproject.infrastructure.repositories.base.ActionException;

public class InvalidCommentException extends ActionException {
    public InvalidCommentException(String message) {
        super(message);
    }
}
