package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.entities.UserEntity;

public class User {
    private final String username;
    private final UserRole userRole;
    private final String userEmail;
    private final Integer phone;

    public User(String username, UserRole userRole, String userEmail, Integer phone) {
        this.username = username;
        this.userRole = userRole;
        this.userEmail = userEmail;
        this.phone = phone;
    }

    public User(UserEntity userEntity) {
        this.username = userEntity.getUserName();
        this.userRole = UserRole.USER;
        this.userEmail = userEntity.getEmail();
        this.phone = userEntity.getPhone();
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

    public Integer getPhone() {
        return phone;
    }
}
