package com.insession.securityproject.infrastructure.entities;

import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_name", length = 55, unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone")
    private Integer phone;

    @Column(name = "role")
    private UserRole role;

    @OneToMany(mappedBy = "user")
    @JoinColumn(name = "topics")
    private List<TopicEntity> topicEntities;

    public UserEntity() {
    }

    public UserEntity(String userName, String password, String email, Integer phone, UserRole role) {
        //TODO: MAKE PEPPER A ENV VARIABLE
        this.password = BCrypt.hashpw(password, BCrypt.gensalt() + "pepper");
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.topicEntities = new ArrayList<>();
    }

    public UserEntity(UserCredentials credentials) {
        this.password = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt() + "pepper");
        this.userName = credentials.getUsername();
        this.email = credentials.getEmail();
        this.phone = credentials.getPhone();
        this.role = UserRole.USER;
        this.topicEntities = new ArrayList<>();
    }

    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt() + "pepper");
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

    public List<TopicEntity> getTopicEntities() {
        return topicEntities;
    }

    public void setTopicEntities(List<TopicEntity> topicEntities) {
        this.topicEntities = topicEntities;
    }
}
