package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.*;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
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

    @Override
    public boolean userExists(String username, String email) {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = em.createQuery("SELECT u FROM UserEntity u WHERE u.userName = :username OR u.email = :email", UserEntity.class)
                    .setParameter("username", username)
                    .setParameter("email", email)
                    .getSingleResult();
            return userEntity != null;
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public void createUser(UserCredentials credentials) throws UserCreationException {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = new UserEntity(
                    credentials.getUsername(), credentials.getPassword(), credentials.getEmail(), credentials.getPhone(), UserRole.USER
            );
            em.persist(userEntity);
        } catch (Exception e) {
            throw new UserCreationException("Unable to create your user... Please try again");
        } finally {
            em.close();
        }
    }

}
