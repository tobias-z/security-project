package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.pincode.PinCodeChannel;

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

    private final Map<String, Map<PinCodeChannel, Integer>> pinCodes;

    private AuthPinCodeService() {
        pinCodes = new HashMap<>();
    }

    public Integer getNewPinCode(String username, PinCodeChannel channel) {
        int pinCode = getRandomPinCode(100000, 999999);
        Map<PinCodeChannel, Integer> channels = getChannel(username);
        channels.put(channel, pinCode);
        Thread thread = new Thread(startInvalidation(channels, channel));
        thread.start();
        return pinCode;
    }

    public boolean isValidPinCode(String username, PinCodeChannel channel, Integer pinCode) {
        synchronized (pinCodes) {
            Map<PinCodeChannel, Integer> channels = pinCodes.get(username);
            if (channels == null) return false;
            Integer thePinCode = channels.get(channel);
            if (thePinCode == null || !thePinCode.equals(pinCode)) return false;
            channels.remove(channel);
            return true;
        }
    }

    private Map<PinCodeChannel, Integer> getChannel(String username) {
        synchronized (pinCodes) {
            Map<PinCodeChannel, Integer> channels = pinCodes.get(username);
            if (channels == null)
                channels = new HashMap<>();
            pinCodes.put(username, channels);
            return channels;
        }
    }

    private Runnable startInvalidation(Map<PinCodeChannel, Integer> channels, PinCodeChannel channel) {
        return () -> {
            try {
                TimeUnit.MINUTES.sleep(5);
                channels.remove(channel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private int getRandomPinCode(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
