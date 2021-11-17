package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.api.services.Log;
import com.insession.securityproject.domain.topic.ITopicRepository;
import com.insession.securityproject.domain.topic.NoTopicsFoundException;
import com.insession.securityproject.domain.topic.Topic;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.infrastructure.entities.TopicEntity;
import com.insession.securityproject.infrastructure.repositories.base.BaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class TopicRepository extends BaseRepository implements ITopicRepository {
    private static final Logger logger = LogManager.getLogger(TopicRepository.class);

    public TopicRepository(EntityManagerFactory emf) {
        super(emf);
    }

    @Override
    public void createTopic(String message, String username) throws UserNotFoundException {
        super.requireUser(username, (user, em) -> {
            TopicEntity topic = new TopicEntity(message, user);
            em.getTransaction().begin();
            em.persist(topic);
            em.getTransaction().commit();
            logger.info("Created new topic by: " + username);
            return null;
        });
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

    @Override
    public Topic getTopic(int id) throws NoTopicsFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            TopicEntity entity = em.find(TopicEntity.class, id);
            if (entity == null)
                throw new NoTopicsFoundException("Did not find a topic with id: " + id);
            return new Topic(entity);
        } finally {
            em.close();
        }
    }
}
