package com.insession.securityproject.infrastructure.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "topicEntity", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "comments")
    private List<CommentEntity> commentEntities;

    public TopicEntity() {
    }

    public TopicEntity(String message, UserEntity user) {
        this.message = message;
        this.user = user;
        this.createdAt = new Date();
        this.commentEntities = new ArrayList<>();
    }

    public void addComment(CommentEntity comment) {
        if (comment != null) {
            this.commentEntities.add(comment);
            comment.setTopicEntity(this);
        }
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

    public List<CommentEntity> getCommentEntities() {
        return commentEntities;
    }
}
