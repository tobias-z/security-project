package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.topic.ITopicRepository;
import com.insession.securityproject.domain.topic.Topic;
import com.insession.securityproject.domain.user.InvalidUserException;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.entities.CommentEntity;
import com.insession.securityproject.infrastructure.entities.TopicEntity;
import com.insession.securityproject.infrastructure.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopicRepositoryTest {

    private ITopicRepository repository;
    private TopicEntity t1, t2, t3;
    private CommentEntity c1, c2, c3;

    private UserEntity admin;

    @BeforeEach
    void setUp() {
        EntityManagerFactory emf = DBConnection.getTestEmf();
        repository = new TopicRepository(emf);

        EntityManager em = emf.createEntityManager();
        UserEntity u1, u2;
        try {
            u1 = new UserEntity("t1", "thebuilder", "bob@thebuilder.com", 13123321, UserRole.USER);
            u2 = new UserEntity("thebuilder", "bob", "thebuilder@bob.com", 32132131, UserRole.USER);
            admin = new UserEntity("admin", "admin", "admin@admin.com", 1132132, UserRole.ADMIN);
            em.getTransaction().begin();
            em.createNamedQuery("CommentEntity.deleteAllRows").executeUpdate();
            em.createNamedQuery("TopicEntity.deleteAllRows").executeUpdate();
            em.createQuery("DELETE FROM UserEntity").executeUpdate();
            em.getTransaction().commit();

            em.getTransaction().begin();
            em.persist(u1);
            em.persist(u2);
            em.persist(admin);
            t2 = new TopicEntity("second", u1);
            t1 = new TopicEntity("first", u2);
            t3 = new TopicEntity("third", u1);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2030, Calendar.NOVEMBER, 10);
            t1.setCreatedAt(calendar.getTime());
            em.persist(t1);
            em.persist(t2);
            em.persist(t3);

            c1 = new CommentEntity("first", t1, t1.getUser());
            c2 = new CommentEntity("second", t1, t1.getUser());
            c3 = new CommentEntity("third", t2, t2.getUser());

            t1.addComment(c1);
            t1.addComment(c2);
            t2.addComment(c3);
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
        List<Topic> topics = repository.getTopics(3);
        assertEquals(t1.getMessage(), topics.get(0).getMessage());
    }

    @Test
    @DisplayName("delete topic will remove a topic with correct username")
    void deleteTopicWillRemoveATopicWithCorrectUsername() throws Exception {
        assertEquals(3, repository.getTopics(3).size());
        assertDoesNotThrow(() -> repository.deleteTopic(t1.getId(), t1.getUser().getUserName()));
        assertEquals(2, repository.getTopics(3).size());
    }

    @Test
    @DisplayName("delete topic will throw when given incorrect username")
    void deleteTopicWillThrowWhenGivenIncorrectUsername() throws Exception {
        assertThrows(InvalidUserException.class, () -> repository.deleteTopic(t1.getId(), t3.getUser().getUserName()));
    }

    @Test
    @DisplayName("delete comment will remove a comment from topic")
    void deleteCommentWillRemoveACommentFromTopic() throws Exception {
        assertEquals(2, repository.getTopic(t1.getId()).getComments().size());
        assertDoesNotThrow(() -> repository.deleteComment(c1.getId(), t1.getUser().getUserName()));
        assertEquals(1, repository.getTopic(t1.getId()).getComments().size());
    }

    @Test
    @DisplayName("user can only delete its own comment")
    void userCanOnlyDeleteItsOwnComment() {
        assertThrows(InvalidUserException.class, () -> repository.deleteComment(c1.getId(), c3.getUserEntity().getUserName()));
    }

    @Test
    @DisplayName("admin can remove any comment")
    void adminCanRemoveAnyComment() {
        assertDoesNotThrow(() -> repository.deleteComment(c1.getId(), admin.getUserName()));
    }

    @Test
    @DisplayName("admin can remove any topic")
    void adminCanRemoveAnyTopic() {
        assertDoesNotThrow(() -> repository.deleteTopic(t1.getId(), admin.getUserName()));
    }
}