package com.insession.securityproject.api.mocks;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;

public class MockUserRepository implements IUserRepository {
    @Override
    public User someMethod(String username) {
        return null;
    }

    @Override
    public User getUserByUserName(String username) {
        return new User("bob", UserRole.USER, "password");
    }
}
