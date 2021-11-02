package com.insession.securityproject.infrastructure.entities;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public class Connection {
    public static EntityManagerFactory getEmf() {
        return getEntityManagerFactory(false);
    }

    public static EntityManagerFactory getTestEmf() {
        return getEntityManagerFactory(true);
    }

    private static EntityManagerFactory getEntityManagerFactory(boolean isTest) {
        if (isTest)
            return Persistence.createEntityManagerFactory("puTest");
        if (System.getenv("DEPLOYED") == null)
            return Persistence.createEntityManagerFactory("pu");

        Properties props = new Properties();
        props.setProperty("javax.persistence.jdbc.user", System.getenv("USER"));
        props.setProperty("javax.persistence.jdbc.password", System.getenv("PW"));
        props.setProperty("javax.persistence.jdbc.url", System.getenv("CONNECTION_STR"));
        props.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        props.setProperty("eclipselink.logging.level","WARNING");
        props.setProperty("eclipselink.logging.level.sql","WARNING");
        return Persistence.createEntityManagerFactory("pu", props);
    }
}
