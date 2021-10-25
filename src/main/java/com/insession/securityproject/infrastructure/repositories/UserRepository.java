package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserNotFoundException;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UserRepository implements IUserRepository {

    private final EntityManagerFactory emf;

    public UserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public UserEntity getUserByUserName(String username) throws UserNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("Select u from UserEntity u where u.userName=:username", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            throw new UserNotFoundException("Not valid login");
        } finally {
            em.close();
        }

    }

}
