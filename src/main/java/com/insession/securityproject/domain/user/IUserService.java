package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;

public interface IUserService {
    User login(String username, String password) throws Exception;

    void sendPinMail(User user);

    UserRole getUserRole(String username) throws UserNotFoundException;
  
    void sendPinSMS(User user);

    User signup(UserCredentials credentials) throws UserCreationException, InvalidKeysException;

    boolean userExists(String username, String email);
}