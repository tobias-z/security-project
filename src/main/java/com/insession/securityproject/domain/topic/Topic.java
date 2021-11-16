package com.insession.securityproject.domain.topic;

import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.infrastructure.entities.TopicEntity;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Topic {
    private String message;
    private User user;

    public Topic() {
    }

    public Topic(TopicEntity entity) {
        this.message = entity.getMessage();
        this.user = new User(entity.getUser());
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
}
