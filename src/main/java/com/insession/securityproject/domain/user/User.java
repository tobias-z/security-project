package com.insession.securityproject.domain.user;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private final String username;
    private final UserRole userRole;
    private String password;

    public User(String username, UserRole userRole) {
        this.username = username;
        this.userRole = userRole;
    }

    public User(User user, String password) {
        this.username = getUsername();
        this.userRole = getUserRole();
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
