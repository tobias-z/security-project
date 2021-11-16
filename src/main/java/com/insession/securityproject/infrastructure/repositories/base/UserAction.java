package com.insession.securityproject.infrastructure.repositories.base;

import com.insession.securityproject.infrastructure.entities.UserEntity;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface UserAction<T> {
    T commit(UserEntity userEntity, EntityManager em);
}
