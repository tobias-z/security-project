package com.insession.securityproject.api.mocks;

import com.insession.securityproject.domain.user.*;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import java.util.List;

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

    @Override
    public void createUser(UserCredentials credentials) throws UserCreationException {
        //TODO (tz): implement this!
        throw new UnsupportedOperationException("Not yet implemented!");
    }

    @Override
    public void create(User user, String password) throws UserCreationException {

    }

    @Override
    public List<UserEntity> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUserByUserName(String username) throws UserNotFoundException {

    }

    @Override
    public void editUser(User user) {

    }
}
