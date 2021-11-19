package com.insession.securityproject.domain.topic;

import com.insession.securityproject.domain.comment.InvalidCommentException;
import com.insession.securityproject.domain.user.UserNotFoundException;

import java.util.List;

public interface ITopicService {
    void createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException;

    List<Topic> getTopics() throws NoTopicsFoundException;

    Topic getTopic(int id) throws NoTopicsFoundException;

    void addCommentToTopic(String comment, String username, int topicId)
            throws UserNotFoundException, InvalidCommentException, NoTopicsFoundException;
}
