package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

//imports for sending mail
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserRepository implements IUserRepository {

    private final EntityManagerFactory emf;

    public UserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }


    @Override
    public User someMethod(String username,String userEmail) {
        // Only done to show how to test services
        // Would be a db call or something
        return new User(username, UserRole.USER, "string");
    }

    @Override
    public User getUserByUserName(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            UserEntity userEntity = em.createQuery("Select u from UserEntity u where u.userName=:username", UserEntity.class).getSingleResult();
            return new User(userEntity);
        } finally {
          em.close();
        }

    }

}
