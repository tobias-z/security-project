package com.insession.securityproject.api.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthTokenService {
    private static AuthTokenService instance;

    public static AuthTokenService getInstance() {
        if (instance == null)
            instance = new AuthTokenService();
        return instance;
    }

    private final Map<String, Integer> tokens;

    private AuthTokenService() {
        tokens = new HashMap<>();
    }

    public Integer getNewToken(String username) {
        int token = getRandomNumber(100000, 999999);
        addToken(username, token);
        Thread thread = new Thread(startInvalidation(username, token));
        thread.start();
        return token;
    }

    public boolean isValidToken(String username, Integer token) {
        synchronized (tokens) {
            Integer theToken = tokens.get(username);
            if (theToken == null || !theToken.equals(token)) return false;
            removeToken(username, theToken);
            return true;
        }
    }

    private void addToken(String key, Integer value) {
        synchronized (tokens) {
            tokens.put(key, value);
        }
    }

    private void removeToken(String key, Integer value) {
        synchronized (tokens) {
            if (!tokens.containsKey(key)) return;
            boolean isSameToken = tokens.get(key).equals(value);
            if (isSameToken)
                tokens.remove(key);
        }
    }

    private Runnable startInvalidation(String username, int token) {
        return () -> {
            try {
                TimeUnit.MINUTES.sleep(2);
                removeToken(username, token);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
