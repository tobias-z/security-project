package com.insession.securityproject.domain.user;

public class User {
    private final String username;
    private final UserRole userRole;

    public User(String username, UserRole userRole) {
        this.username = username;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
