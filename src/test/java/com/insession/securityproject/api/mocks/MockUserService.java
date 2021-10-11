package com.insession.securityproject.api.mocks;

import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;

public class MockUserService implements IUserService {
    @Override
    public User someMethod(String username) {
        return new User(username, UserRole.USER);
    }
}