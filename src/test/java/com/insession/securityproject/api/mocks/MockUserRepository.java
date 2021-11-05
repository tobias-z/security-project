package com.insession.securityproject.api.mocks;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.entities.UserEntity;

public class MockUserRepository implements IUserRepository {

    @Override
    public UserEntity getUserByUserName(String username) {
        return new UserEntity("bob", "password", "dsada", 32132131, UserRole.USER);
    }

    @Override
    public boolean userExists(String username, String email) {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}
