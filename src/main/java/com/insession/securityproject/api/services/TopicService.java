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
    public void createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException {
        repo.createTopic(message, username);
    }

    @Override
    public List<Topic> getTopics() throws NoTopicsFoundException {
        return repo.getTopics(100);
    }

    @Override
    public Topic getTopic(int id) throws NoTopicsFoundException {
        return repo.getTopic(id);
    }
}
