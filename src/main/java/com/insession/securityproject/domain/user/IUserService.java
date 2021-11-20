package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import java.util.List;

public interface IUserService {
    User login(String username, String password) throws Exception;

    void sendPinMail(User user);

    UserRole getUserRole(String username) throws UserNotFoundException;
  
    void sendPinSMS(User user);

    void signup(UserCredentials credentials, int emailPin, int smsPin) throws UserCreationException;

    void create(User user,String password) throws UserCreationException;

    List<User> getAll() throws UserNotFoundException;

    boolean userExists(String username, String email);

    void deleteUserByUserName(String username) throws UserNotFoundException;

    void editUserName(String usertoedit);

    User getUserByUserName(String username) throws UserNotFoundException;

}
