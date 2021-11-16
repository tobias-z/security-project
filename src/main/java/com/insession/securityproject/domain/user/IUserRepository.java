package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import com.insession.securityproject.infrastructure.entities.UserEntity;

public interface IUserRepository {
    UserEntity getUserByUserName(String username) throws UserNotFoundException;

    boolean userExists(String username, String email);

    void createUser(UserCredentials userCredentials) throws UserCreationException;

    void create(User user,String password)throws UserCreationException;
}
