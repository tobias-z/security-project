package com.insession.securityproject.infrastructure.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "comment")
@NamedQueries({
        @NamedQuery(name = "CommentEntity.deleteAllRows", query = "DELETE from CommentEntity u"),
        @NamedQuery(name = "CommentEntity.getAll", query = "Select u from CommentEntity u")
})
public class CommentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "topic")
    private TopicEntity topicEntity;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity userEntity;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public CommentEntity() {
    }

    public CommentEntity(String message, TopicEntity topicEntity, UserEntity userEntity) {
        this.message = message;
        this.topicEntity = topicEntity;
        this.userEntity = userEntity;
        this.createdAt = new Date();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public CommentEntity(String message, TopicEntity topicEntity, UserEntity userEntity, Date createdAt) {
        this.message = message;
        this.topicEntity = topicEntity;
        this.userEntity = userEntity;
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TopicEntity getTopicEntity() {
        return topicEntity;
    }

    public void setTopicEntity(TopicEntity topicEntity) {
        this.topicEntity = topicEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
