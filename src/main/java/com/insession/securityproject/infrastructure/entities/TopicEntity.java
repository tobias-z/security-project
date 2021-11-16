package com.insession.securityproject.infrastructure.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "topic")
@NamedQueries({
        @NamedQuery(name = "TopicEntity.deleteAllRows", query = "DELETE from TopicEntity"),
        @NamedQuery(name = "TopicEntity.getAll", query = "Select u from TopicEntity u")
})
public class TopicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @JoinColumn(name = "user")
    @ManyToOne
    private UserEntity user;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public TopicEntity() {
    }

    public TopicEntity(String message, UserEntity user) {
        this.message = message;
        this.user = user;
        this.createdAt = new Date();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
