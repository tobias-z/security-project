package com.insession.securityproject.infrastructure.repositories;

import com.insession.securityproject.api.services.UserService;
import com.insession.securityproject.domain.user.IUserRepository;
import com.insession.securityproject.domain.user.IUserService;
import com.insession.securityproject.domain.user.User;
import com.insession.securityproject.domain.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private IUserRepository repository;
    private EntityManagerFactory emf;


    private  IUserService service;//---------
    @BeforeEach
    void setUp() {
        repository = new UserRepository();
        service=new UserService(repository);
       // emf = Persistence.createEntityManagerFactory("puTest");
        // Do some creating of test users
    }

    @Test
    void someMethod() {
        User user = repository.someMethod("bob","jfg@insession.dk");
        assertEquals("bob", user.getUsername());
    }
    @Test
    void sendMail(){
        User user = new User("bob", UserRole.USER,"jensgelbek@gmail.com");
        service.sendPinMail(user);
    }

}