package com.insession.securityproject.domain.user;

import com.insession.securityproject.infrastructure.entities.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private final String username;
    private final UserRole userRole;
    private final String password;
    private final String userEmail;
  
    public User(String username, UserRole userRole, String password) {
        this.username = username;
        this.userRole = userRole;
        this.password = password;
    }

    public User(UserEntity userEntity) {
        this.username = userEntity.getUserName();
        this.password = userEntity.getPassword();
        this.userRole = UserRole.USER;
    }

    public User(String username, UserRole userRole, String userEmail) {
        this.username = username;
        this.userRole = userRole;
        this.userEmail=userEmail;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getUserRole() {
        return userRole;
    }
  
    public String getPassword() {
        return password;
    }

    public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw, password);
    }  
      
    public String getUserEmail() {
        return userEmail;
    }
}
