package com.insession.securityproject.api.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

public class NullValidatorService {
    private static final Logger logger = LogManager.getLogger(TopicService.class);

    public static <T extends Exception, F> void nullOrEmpty(F field, String fieldName, Class<T> exception) throws T {
        if (field == null)
            throwException(fieldName, exception);
        if (field instanceof String && ((String) field).isEmpty())
            throwException(fieldName, exception);
    }

    private static <T extends Exception> void throwException(String fieldName, Class<T> exception) throws T {
        try {
            String message = "No " + fieldName + " was provided. but required for this action";
            logger.warn(message);
            throw exception.getConstructor(String.class).newInstance(message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error("Unable to throw: " + exception.getName() + ". " + fieldName);
        }
    }
}
