package com.insession.securityproject.domain.topic;

import com.insession.securityproject.domain.comment.Comment;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.infrastructure.entities.TopicEntity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Topic {
    private Integer id;
    private String message;
    private User user;
    private String createdAt;
    private List<Comment> comments;

    public Topic() {
    }

    public Topic(TopicEntity entity) {
        this.id = entity.getId();
        this.message = entity.getMessage();
        this.user = new User(entity.getUser());
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.createdAt = formatter.format(entity.getCreatedAt().getTime());
        this.comments = Comment.getComments(entity.getCommentEntities());
    }

    public static List<Topic> getTopics(List<TopicEntity> entities) {
        return entities.stream()
                .map(Topic::new)
                .collect(Collectors.toList());
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
