package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.topic.ITopicRepository;
import com.insession.securityproject.domain.topic.Topic;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.entities.TopicEntity;
import com.insession.securityproject.infrastructure.entities.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopicRepositoryTest {

    private ITopicRepository repository;
    private TopicEntity t1, t2, t3;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = DBConnection.getTestEmf();
        repository = new TopicRepository(emf);

        EntityManager em = emf.createEntityManager();
        UserEntity u1, u2;
        try {
            u1 = new UserEntity("t1", "thebuilder", "bob@thebuilder.com", 13123321, UserRole.USER);
            u2 = new UserEntity("thebuilder", "bob", "thebuilder@bob.com", 32132131, UserRole.USER);
            em.getTransaction().begin();
            em.persist(u1);
            em.persist(u2);
            t1 = new TopicEntity("first", u1);
            t2 = new TopicEntity("second", u1);
            t3 = new TopicEntity("third", u2);
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    @DisplayName("get topics will return all created topics")
    void getTopicsWillReturnAllCreatedTopics() throws Exception {
        assertEquals(3, repository.getTopics(10).size());
    }

    @Test
    @DisplayName("get topics will return less given a limit")
    void getTopicsWillReturnLessGivenALimit() throws Exception {
        assertEquals(1, repository.getTopics(1).size());
    }

    @Test
    @DisplayName("get topics will send the newest topics first")
    void getTopicsWillSendTheNewestTopicsFirst() throws Exception {
        assertEquals(t3.getMessage(), repository.getTopics(10).get(2).getMessage());
    }
}