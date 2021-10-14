package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;

public class UserService implements IUserService {
    private final IUserRepository repository;

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    // Only used to give example of testing the services
    @Override
    public User someMethod(String username) {
        return repository.someMethod(username);
    }

    @Override
    public User login(String username, String password) throws Exception {
        User user = repository.getUserByUserName(username);
        if (!user.verifyPassword(password)) {
            throw new Exception("Not valid login");
        }
        return user;
    }


}
