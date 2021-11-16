package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.topic.*;
import com.insession.securityproject.domain.user.UserNotFoundException;

import java.util.List;

public class TopicService implements ITopicService {
    private final ITopicRepository repo;

    public TopicService(ITopicRepository repository) {
        this.repo = repository;
    }

    @Override
    public Topic createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException {
        //TODO: validate fields with new validation function
        return repo.createTopic(message, username);
    }

    @Override
    public List<Topic> getTopics() throws NoTopicsFoundException {
        return repo.getTopics(100);
    }
}
