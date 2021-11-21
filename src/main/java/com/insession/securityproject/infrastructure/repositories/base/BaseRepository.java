package com.insession.securityproject.infrastructure.repositories.base;

import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.infrastructure.entities.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;

public class BaseRepository {
    private static final Logger logger = LogManager.getLogger(BaseRepository.class);

    protected UserEntity getUserEntity(String username, EntityManager em) throws UserNotFoundException {
        try {
            return em.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.userName = :u", UserEntity.class
                    ).setParameter("u", username)
                    .getSingleResult();
        } catch(Exception e) {
            logger.warn("User: " + username + " was requested. But none existent");
            throw new UserNotFoundException("Did not find user with username: " + username);
        }
    }
}
