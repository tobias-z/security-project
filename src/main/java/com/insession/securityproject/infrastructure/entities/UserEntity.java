package com.insession.securityproject.infrastructure.entities;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.Date;


@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name",length = 55, unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "role")
    private UserRole role;

    @Column(name = "last_active")
    @Temporal(TemporalType.DATE)
    private Date lastActive;

    public UserEntity() {}

    public UserEntity(String userName, String password, String email, Integer phone, UserRole role) {
        //TODO: MAKE PEPPER A ENV VARIABLE
        this.password = BCrypt.hashpw(password, BCrypt.gensalt() + "pepper");
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.lastActive = new Date();
    }

    public UserEntity(UserCredentials credentials) {
        this.password = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt() + "pepper");
        this.userName = credentials.getUsername();
        this.email = credentials.getEmail();
        this.phone = credentials.getPhone();
        this.role = UserRole.USER;
        this.lastActive = new Date();
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

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getLastActive() {
        return lastActive;
    }

    public void updateActiveDate() {
        this.lastActive = new Date();
    }
}
