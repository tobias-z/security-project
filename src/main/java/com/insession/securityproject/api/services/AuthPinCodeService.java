package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.pincode.PinCodeChannel;
import com.insession.securityproject.infrastructure.cache.Redis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthPinCodeService {

    private String getRedisString(String username, PinCodeChannel channel) {
        return "#pincode" + username + channel.toString();
    }

    public Integer getNewPinCode(String username, PinCodeChannel channel) {
        int pinCode = getRandomPinCode(100000, 999999);
        Redis.getConnection().put(getRedisString(username, channel), pinCode).close();
        new Thread(startInvalidation(username, channel)).start();
        return pinCode;
    }

    public boolean isValidPinCode(String username, PinCodeChannel channel, Integer pinCode) {
        String key = getRedisString(username, channel);
        Integer thePinCode = Redis.getConnection().get(key, Integer.class);
        if (thePinCode == null || !thePinCode.equals(pinCode))
            return false;

        Redis.getConnection().pop(key).close();
        return true;
    }

    private Runnable startInvalidation(String username, PinCodeChannel channel) {
        return () -> {
            try {
                TimeUnit.MINUTES.sleep(5);
                Redis.getConnection().pop(getRedisString(username, channel));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private int getRandomPinCode(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
