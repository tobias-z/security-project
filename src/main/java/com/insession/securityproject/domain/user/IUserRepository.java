package com.insession.securityproject.domain.user;

public interface IUserRepository {
    User someMethod(String username);
    User getUserByUserName(String username);

}
