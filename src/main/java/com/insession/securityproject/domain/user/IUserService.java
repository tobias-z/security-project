package com.insession.securityproject.domain.user;

public interface IUserService {
    User someMethod(String username,String UserEmail);
    void sendPinMail(User user);
}