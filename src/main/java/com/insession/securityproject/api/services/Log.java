package com.insession.securityproject.api.services;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log {

    static {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        }

        private static Logger logger = LogManager.getLogger(Log.class);

        public static void main(String[] args) {
            logger.debug("Debug log message");
            logger.info("Info log message");
            logger.error("Error log message");
            logger.fatal("");
            logger.warn("");
        }
    }
