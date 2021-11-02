package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import com.insession.securityproject.infrastructure.entities.Connection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest {

    private IUserRepository repository;
    private EntityManagerFactory emf;

    private IUserService service;//---------

    @BeforeEach
    void setUp() {
        repository = new UserRepository(emf);
        emf = Connection.getTestEmf();
        // Do some creating of test users
    }



}