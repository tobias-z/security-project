package com.insession.securityproject.domain.comment;

import com.insession.securityproject.domain.topic.Topic;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.infrastructure.entities.CommentEntity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Comment {
    private Integer id;
    private String message;
    private User user;
    private String createdAt;

    public Comment(CommentEntity entity) {
        this.id = entity.getId();
        this.message = entity.getMessage();
        this.user = new User(entity.getUserEntity());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.createdAt = formatter.format(entity.getCreatedAt().getTime());
    }

    public static List<Comment> getComments(List<CommentEntity> commentEntities) {
        return commentEntities.stream()
                .map(Comment::new)
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
