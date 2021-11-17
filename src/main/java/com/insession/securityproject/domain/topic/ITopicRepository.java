package com.insession.securityproject.domain.topic;

import com.insession.securityproject.infrastructure.repositories.base.ActionException;

import java.util.List;

public interface ITopicRepository {
    void createTopic(String message, String username) throws InvalidTopicException, ActionException;

    List<Topic> getTopics(int limit) throws NoTopicsFoundException;

    Topic getTopic(int id) throws NoTopicsFoundException;

    void addCommentToTopic(String comment, String username, int topicId) throws ActionException;
}
