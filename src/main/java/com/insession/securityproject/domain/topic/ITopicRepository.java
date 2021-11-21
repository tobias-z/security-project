package com.insession.securityproject.domain.topic;

import com.insession.securityproject.domain.comment.InvalidCommentException;
import com.insession.securityproject.domain.comment.CommentNotFoundException;
import com.insession.securityproject.domain.user.InvalidUserException;
import com.insession.securityproject.domain.user.UserNotFoundException;

import java.util.List;

public interface ITopicRepository {
    int createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException;

    List<Topic> getTopics(int limit) throws NoTopicsFoundException;

    Topic getTopic(int id) throws NoTopicsFoundException;

    void addCommentToTopic(String comment, String username, int topicId) throws UserNotFoundException, InvalidCommentException, NoTopicsFoundException;

    void deleteTopic(Integer topicId, String username) throws NoTopicsFoundException, InvalidUserException, UserNotFoundException;

    void deleteComment(int commentId, String username) throws UserNotFoundException, InvalidUserException, CommentNotFoundException;
}
