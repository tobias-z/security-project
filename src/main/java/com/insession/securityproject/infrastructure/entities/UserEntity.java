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


    public UserEntity() {}

    public UserEntity(String userName, String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt() + "pepper");
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getPassword() {
        return password;
    }

    public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw, password);
    }
}
