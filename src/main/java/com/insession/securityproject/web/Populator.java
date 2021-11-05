package com.insession.securityproject.web;

import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.DBConnection;
import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Populator {
    public static void main(String[] args) {
        EntityManagerFactory emf = DBConnection.getEmf();
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM UserEntity").executeUpdate();
            em.persist(new UserEntity("bob", "theBuilder", "tobias.zimmer007@gmail.com", 3213131, UserRole.USER));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
