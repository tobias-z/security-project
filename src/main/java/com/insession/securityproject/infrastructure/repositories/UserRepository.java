package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.entities.UserEntity;

public class UserRepository implements IUserRepository {
    @Override
    public User someMethod(String username) {
        // Only done to show how to test services
        // Would be a db call or something
        return new User(username, UserRole.USER);
    }

    // Create USer DTO
    // public createUSer(UserEntity userEntity) {
    //    retrun new User(userEntity.getUserName())
    //}

}
