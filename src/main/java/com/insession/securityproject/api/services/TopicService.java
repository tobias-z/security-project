package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.topic.*;
import com.insession.securityproject.infrastructure.repositories.base.ActionException;

import java.util.List;

public class TopicService implements ITopicService {
    private final ITopicRepository repo;

    public TopicService(ITopicRepository repository) {
        this.repo = repository;
    }

    @Override
    public void createTopic(String message, String username) throws InvalidTopicException {
        try {
            repo.createTopic(message, username);
        } catch (com.insession.securityproject.infrastructure.repositories.base.ActionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Topic> getTopics() throws NoTopicsFoundException {
        return repo.getTopics(100);
    }

    @Override
    public Topic getTopic(int id) throws NoTopicsFoundException {
        return repo.getTopic(id);
    }

    @Override
    public void addCommentToTopic(String comment, String username, int topicId) throws ActionException {
        repo.addCommentToTopic(comment, username, topicId);
    }
}
