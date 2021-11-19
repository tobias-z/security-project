package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.comment.InvalidCommentException;
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
        NullValidatorService.validateField(message, "message", InvalidTopicException.class);
        NullValidatorService.validateField(username, "username", UserNotFoundException.class);
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

    @Override
    public void addCommentToTopic(String comment, String username, int topicId) throws InvalidCommentException, UserNotFoundException, NoTopicsFoundException {
        NullValidatorService.validateField(comment, "comment", InvalidCommentException.class);
        NullValidatorService.validateField(username, "username", UserNotFoundException.class);
        repo.addCommentToTopic(comment, username, topicId);
    }


}
