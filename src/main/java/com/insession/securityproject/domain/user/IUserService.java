package com.insession.securityproject.domain.user;

public interface IUserService {
    User login(String username, String password) throws Exception;

    void sendPinMail(User user);

    UserRole getUserRole(String username) throws UserNotFoundException;
  
    void sendPinSMS(User user);
}