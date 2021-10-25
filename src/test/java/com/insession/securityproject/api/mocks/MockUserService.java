package com.insession.securityproject.api.mocks;

import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;

public class MockUserService implements IUserService {
    @Override
    public User someMethod(String username) {
        return new User(username, UserRole.USER, "string");
    }

    @Override
    public User login(String username, String password) {
        return null;
    public User someMethod(String username, String userName) {
        return new User(username, UserRole.USER, userName);
    }

    @Override
    public void sendPinMail( User user) {

    }
}
