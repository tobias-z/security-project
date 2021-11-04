package com.insession.securityproject.domain.user;

public interface IUserService {
    User login(String username, String password) throws Exception;

    void sendPinMail(User user);

    UserRole getUserRole(String username) throws UserNotFoundException;
  
    void sendPinSMS(User user);

    User signup(String username, String email, Integer phone, String password) throws UserCreationException, InvalidKeysException;
}