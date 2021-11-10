package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.infrastructure.DBConnection;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest {

    private IUserRepository repository;
    private EntityManagerFactory emf;

    private IUserService service;//---------

    @BeforeEach
    void setUp() {
        repository = new UserRepository(emf);
        emf = DBConnection.getTestEmf();
        // Do some creating of test users
    }



}