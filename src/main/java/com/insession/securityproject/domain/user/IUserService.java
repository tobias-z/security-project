package com.insession.securityproject.domain.user;

public interface IUserService {
    User someMethod(String username);
    User login(String username, String password) throws Exception;
    User someMethod(String username,String UserEmail);
    void sendPinMail(User user);
}