package com.insession.securityproject.infrastructure.repositories.base;

import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class BaseRepository {
    protected final EntityManagerFactory emf;

    public BaseRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    protected <T> T requireUser(String username, UserAction<T> action) throws ActionException {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = em.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.userName = :u", UserEntity.class
                    ).setParameter("u", username)
                    .getSingleResult();
            return action.commit(userEntity, em);
        } catch(NoResultException e) {
            throw new UserNotFoundException("Did not find user with username: " + username);
        } finally {
            em.close();
        }
    }
}
