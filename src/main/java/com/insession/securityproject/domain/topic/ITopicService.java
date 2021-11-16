package com.insession.securityproject.domain.topic;

import com.insession.securityproject.domain.user.UserNotFoundException;

import java.util.List;

public interface ITopicService {
    Topic createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException;
    List<Topic> getTopics() throws NoTopicsFoundException;
}
