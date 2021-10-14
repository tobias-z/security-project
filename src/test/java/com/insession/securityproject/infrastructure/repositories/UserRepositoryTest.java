package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private IUserRepository repository;
    private EntityManagerFactory emf;

    @BeforeEach
    void setUp() {
        repository = new UserRepository(emf);
        emf = Persistence.createEntityManagerFactory("puTest");
        // Do some creating of test users
    }

    @Test
    void someMethod() {
        User user = repository.someMethod("bob");
        assertEquals("bob", user.getUsername());
    }
}