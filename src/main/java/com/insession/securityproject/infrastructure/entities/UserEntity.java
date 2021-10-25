package com.insession.securityproject.infrastructure.entities;
import com.insession.securityproject.domain.user.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.management.relation.Role;
import javax.persistence.*;


@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name",length = 55)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public UserEntity() {}

    public UserEntity(String userName, String password, String email) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt() + "pepper");
        this.userName = userName;
        this.email = email;
    }

    public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt() + "pepper");
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
