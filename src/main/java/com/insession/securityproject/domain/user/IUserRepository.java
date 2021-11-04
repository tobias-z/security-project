package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.entities.UserEntity;

public interface IUserRepository {
    UserEntity getUserByUserName(String username) throws UserNotFoundException;

    boolean userExists(String username, String email);
}
