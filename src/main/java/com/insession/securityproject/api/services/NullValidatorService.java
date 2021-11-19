package com.insession.securityproject.api.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

public class NullValidatorService {
    private static final Logger logger = LogManager.getLogger(TopicService.class);

    public static <T extends Exception> void validateField(String field, String fieldName, Class<T> exception) throws T {
        if (field == null || field.isEmpty()) {
            try {
                String message = "No " + fieldName + " was provided. but required for this action";
                throw exception.getConstructor().newInstance(message);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger.error("Unable to throw: " + exception.getName() + ". " + field);
            }
        }
    }
}
