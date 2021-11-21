package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.comment.InvalidCommentException;
import com.insession.securityproject.domain.comment.CommentNotFoundException;
import com.insession.securityproject.domain.topic.*;
import com.insession.securityproject.domain.user.InvalidKeysException;
import com.insession.securityproject.domain.user.InvalidUserException;
import com.insession.securityproject.domain.user.UserNotFoundException;

import java.util.List;

public class TopicService implements ITopicService {
    private final ITopicRepository repo;

    public TopicService(ITopicRepository repository) {
        this.repo = repository;
    }

    @Override
    public int createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException {
        NullValidatorService.nullOrEmpty(message, "message", InvalidTopicException.class);
        NullValidatorService.nullOrEmpty(username, "username", UserNotFoundException.class);
        return repo.createTopic(message, username);
    }

    @Override
    public List<Topic> getTopics() throws NoTopicsFoundException {
        return repo.getTopics(100);
    }

    @Override
    public Topic getTopic(int id) throws NoTopicsFoundException, InvalidTopicException {
        NullValidatorService.nullOrEmpty(id, "topicId", InvalidTopicException.class);
        return repo.getTopic(id);
    }

    @Override
    public void addCommentToTopic(String comment, String username, int topicId) throws InvalidCommentException, UserNotFoundException, NoTopicsFoundException {
        NullValidatorService.nullOrEmpty(comment, "comment", InvalidCommentException.class);
        NullValidatorService.nullOrEmpty(username, "username", UserNotFoundException.class);
        repo.addCommentToTopic(comment, username, topicId);
    }

    @Override
    public void deleteTopic(Integer topicId, String username) throws InvalidKeysException, UserNotFoundException, NoTopicsFoundException, InvalidUserException {
        NullValidatorService.nullOrEmpty(topicId, "topicId", InvalidKeysException.class);
        NullValidatorService.nullOrEmpty(username, "username", UserNotFoundException.class);
        repo.deleteTopic(topicId, username);
    }

    @Override
    public void deleteComment(int commentId, String username) throws InvalidKeysException, UserNotFoundException, InvalidUserException, CommentNotFoundException {
        NullValidatorService.nullOrEmpty(commentId, "commentId", InvalidKeysException.class);
        NullValidatorService.nullOrEmpty(username, "username", UserNotFoundException.class);
        repo.deleteComment(commentId, username);
    }


}
