package com.insession.securityproject.domain.topic;

import com.insession.securityproject.domain.user.UserNotFoundException;

import java.util.List;

public interface ITopicRepository {
    Topic createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException;
    List<Topic> getTopics(int limit) throws NoTopicsFoundException;
}
