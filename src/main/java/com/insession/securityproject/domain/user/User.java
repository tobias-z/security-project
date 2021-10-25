package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.entities.UserEntity;

public class User {
    private final String username;
    private final UserRole userRole;
    private final String userEmail;
  
    public User(String username, UserRole userRole, String userEmail) {
        this.username = username;
        this.userRole = userRole;
        this.userEmail = userEmail;
    }

    public User(UserEntity userEntity) {
        this.username = userEntity.getUserName();
        this.userRole = UserRole.USER;
        this.userEmail = userEntity.getEmail();
    }

    public String getUsername() {
        return username;
    }

    public UserRole getUserRole() {
        return userRole;
    }
  
    public String getUserEmail() {
        return userEmail;
    }
}
