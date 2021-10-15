package com.insession.securityproject.api.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthPinCodeService {
    private static AuthPinCodeService instance;

    public static AuthPinCodeService getInstance() {
        if (instance == null)
            instance = new AuthPinCodeService();
        return instance;
    }

    private final Map<String, Integer> pinCodes;

    private AuthPinCodeService() {
        pinCodes = new HashMap<>();
    }

    public Integer getNewToken(String username) {
        int pinCode = getRandomPinCode(100000, 999999);
        addPinCode(username, pinCode);
        Thread thread = new Thread(startInvalidation(username, pinCode));
        thread.start();
        return pinCode;
    }

    public boolean isValidToken(String username, Integer token) {
        synchronized (pinCodes) {
            Integer thePinCode = pinCodes.get(username);
            if (thePinCode == null || !thePinCode.equals(token)) return false;
            removePinCode(username, thePinCode);
            return true;
        }
    }

    private void addPinCode(String username, Integer pinCode) {
        synchronized (pinCodes) {
            pinCodes.put(username, pinCode);
        }
    }

    private void removePinCode(String username, Integer pinCode) {
        synchronized (pinCodes) {
            if (!pinCodes.containsKey(username)) return;
            boolean isSameToken = pinCodes.get(username).equals(pinCode);
            if (isSameToken)
                pinCodes.remove(username);
        }
    }

    private Runnable startInvalidation(String username, int pinCode) {
        return () -> {
            try {
                TimeUnit.MINUTES.sleep(5);
                removePinCode(username, pinCode);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private int getRandomPinCode(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
