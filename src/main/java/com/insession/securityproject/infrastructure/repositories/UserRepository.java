package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.*;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.cache.saved.UserCredentials;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


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
            UserEntity userEntity = new UserEntity(credentials);
            em.getTransaction().begin();
            em.persist(userEntity);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new UserCreationException("Unable to create your user... Please try again");
        } finally {
            em.close();
        }
    }

    @Override
    public void create(User user,String password) throws UserCreationException {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = new UserEntity(user.getUsername(), password, user.getUserEmail(), user.getPhone(), user.getUserRole());
            em.getTransaction().begin();
            em.persist(userEntity);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new UserCreationException("Unable to create your user... Please try again");
        } finally {
            em.close();
        }
    }

    @Override
    public List<UserEntity> getAllUsers() throws UserNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("Select u from UserEntity u", UserEntity.class)
                    .getResultList();
        } catch (Exception e) {
            throw new UserNotFoundException("No Users found");
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteUserByUserName(String username) throws UserNotFoundException {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity=em.createQuery("Select u from UserEntity u where u.userName=:username", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
            em.getTransaction().begin();
            em.remove(userEntity);
            em.getTransaction().commit();
            return;
        } catch (Exception e) {
            System.out.println("catch");
            return ;
        } finally {
            em.close();
        }
    }

    @Override
    public void editUser(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity=em.createQuery("Select u from UserEntity u where u.userName=:username", UserEntity.class)
                    .setParameter("username", user.getUsername())
                    .getSingleResult();
            em.getTransaction().begin();
                 userEntity.setEmail(user.getUserEmail());
                 userEntity.setPhone(user.getPhone());
                 userEntity.setRole(user.getUserRole());
            em.getTransaction().commit();
            return;
        } catch (Exception e) {
            System.out.println("catch");
            return ;
        } finally {
            em.close();
        }
    }

    @Override
    public void createUserFile(String username, String fileName) {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = em.createQuery("Select u from UserEntity u where u.userName=:username", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
            em.getTransaction().begin();
            userEntity.setImageFile(fileName);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Could not set fileName");
        } finally {
            em.close();
        }

    }

    @Override
    public String getUserImageFile(String username) {
        String filename = "";
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = em.createQuery("Select u from UserEntity u where u.userName=:username", UserEntity.class)
                    .setParameter("username", username )
                    .getSingleResult();
            em.getTransaction().begin();
            filename = userEntity.getImageFile();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Could not set fileName");
        } finally {
            em.close();
        }
     return filename;
    }

    @Override
    public void deleteImageFile(String username) throws UserNotFoundException {

    }


}




