package com.insession.securityproject.web;

import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Populator {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM UserEntity").executeUpdate();
            em.persist(new UserEntity("bob", "theBuilder", "cph-pa127@cphbusiness.dk"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
