package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.IUserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log {

        private static Level consent = Level.forName("CONCENT", 550);
        private static Logger logger = LogManager.getLogger(Log.class);

        public static void main(String[] args) {
            logger.debug("Debug log message");
            logger.log(consent, "I give my consent");
            logger.info("Info log message");
            logger.error("Error log message");
            logger.fatal("noget");
            logger.warn("What's down");
        }


    }
