package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.comment.InvalidCommentException;
import com.insession.securityproject.domain.topic.ITopicRepository;
import com.insession.securityproject.domain.topic.InvalidTopicException;
import com.insession.securityproject.domain.topic.NoTopicsFoundException;
import com.insession.securityproject.domain.topic.Topic;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.infrastructure.entities.CommentEntity;
import com.insession.securityproject.infrastructure.entities.TopicEntity;
import com.insession.securityproject.infrastructure.entities.UserEntity;
import com.insession.securityproject.infrastructure.repositories.base.BaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class TopicRepository extends BaseRepository implements ITopicRepository {
    private static final Logger logger = LogManager.getLogger(TopicRepository.class);
    private final EntityManagerFactory emf;

    public TopicRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void createTopic(String message, String username) throws InvalidTopicException, UserNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity user = super.getUserEntity(username, em);
            TopicEntity topic = new TopicEntity(message, user);
            em.getTransaction().begin();
            em.persist(topic);
            em.getTransaction().commit();
            logger.info("Created new topic by: " + username);
        } catch (EntityExistsException e) {
            logger.error("Invalid topic provided by " + username + ": " + message);
            throw new InvalidTopicException("Invalid topic. Please provide a valid one");
        } finally {
            em.close();
        }
    }

    @Override
    public List<Topic> getTopics(int limit) throws NoTopicsFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            List<TopicEntity> topicEntities = em.createNativeQuery(
                            "SELECT * FROM topic ORDER BY created_at ASC LIMIT ?", TopicEntity.class
                    ).setParameter(1, limit)
                    .getResultList();
            return Topic.getTopics(topicEntities);
        } catch (Exception e) {
            logger.warn("No topics where found. Did someone remove them?");
            throw new NoTopicsFoundException("No topics where found");
        } finally {
            em.close();
        }
    }

    private TopicEntity getTopicEntity(int topicId, EntityManager em) throws NoTopicsFoundException {
        TopicEntity entity = em.find(TopicEntity.class, topicId);
        if (entity == null) {
            logger.info("Someone tried to access an invalid topic with id: " + topicId);
            throw new NoTopicsFoundException("Did not find a topic with id: " + topicId);
        }
        return entity;
    }

    @Override
    public Topic getTopic(int id) throws NoTopicsFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            return new Topic(getTopicEntity(id, em));
        } finally {
            em.close();
        }
    }

    @Override
    public void addCommentToTopic(String comment, String username, int topicId)
            throws InvalidCommentException, UserNotFoundException, NoTopicsFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = super.getUserEntity(username, em);
            TopicEntity topicEntity = getTopicEntity(topicId, em);
            em.getTransaction().begin();
            topicEntity.addComment(new CommentEntity(comment, topicEntity, userEntity));
            em.getTransaction().commit();
        } catch (EntityExistsException e) {
            logger.error("Invalid comment provided by " + username + ": " + comment);
            throw new InvalidCommentException("Invalid comment. Please provide a valid one");
        } finally {
            em.close();
        }
    }
}
