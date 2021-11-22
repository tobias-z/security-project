package com.insession.securityproject.api.services;

import com.insession.securityproject.infrastructure.cache.Redis;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class IPDelayService {
    private static final Logger logger = LogManager.getLogger(IPDelayService.class);

    private final int delaySeconds;

    public IPDelayService(int delaySeconds) {
        this.delaySeconds = delaySeconds;
    }

    public void handleDelay(String ip) {
        if (shouldDelayRequest(ip)) {
            try {
                TimeUnit.SECONDS.sleep(delaySeconds);
            } catch (InterruptedException e) {
                logger.error("failed to delay post on ip: " + ip);
            }
        }

        Redis.getConnection()
                .put(getKey(ip), LocalDateTime.now().plusSeconds(delaySeconds).toString())
                .close();
    }

    private boolean shouldDelayRequest(String ip) {
        String lastRequest = Redis.getConnection().get(getKey(ip), String.class);
        if (lastRequest == null) return false;

        LocalDateTime lastDate = LocalDateTime.parse(lastRequest);
        return lastDate.isBefore(LocalDateTime.now());
    }

    private String getKey(String ip) {
        return "#ipdelay" + ip;
    }
}
