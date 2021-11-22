package com.insession.securityproject.api.services;

import com.insession.securityproject.domain.user.BruteForceException;
import com.insession.securityproject.infrastructure.cache.Redis;
import com.insession.securityproject.infrastructure.cache.saved.BruteForceCount;
import com.insession.securityproject.infrastructure.cache.saved.BruteForceIP;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class BruteForceService {
    private final Integer maxTries;
    private final Integer userTries;

    public BruteForceService(Integer maxTries, Integer userTries) {
        this.maxTries = maxTries;
        this.userTries = userTries;
    }

    public void handleBruteForce(String ip, String username) throws BruteForceException {
        BruteForceIP bruteForceIP = getBruteForceIP(ip);
        BruteForceCount bruteForceCount = getBruteForceCount(username, bruteForceIP);
        bruteForceCount.incrementCount();
        bruteForceIP.incrementCount();

        Redis.getConnection().put(getKey(ip), bruteForceIP).close();

        if (isBanned(bruteForceIP, bruteForceCount))
            throw new BruteForceException();
    }

    private boolean isBanned(BruteForceIP bruteForceIP, BruteForceCount bruteForceCount) {
        if (bruteForceCount.getCount() > userTries)
            return true;
        if (bruteForceIP.getFullCount() > maxTries)
            return true;

        int count = 0;
        for (Map.Entry<String, BruteForceCount> entry : bruteForceIP.getBruteForceCount().entrySet()) {
            if (entry.getValue().getCount() >= userTries) count++;
        }

        return count >= 3;
    }

    private BruteForceCount getBruteForceCount(String username, BruteForceIP bruteForceIP) {
        BruteForceCount bruteForceCount = bruteForceIP.getBruteForceCount().get(username);
        if (bruteForceCount != null)
            return bruteForceCount;

        bruteForceCount = new BruteForceCount(username, 0, LocalDateTime.now());
        bruteForceIP.getBruteForceCount().put(username, bruteForceCount);
        return bruteForceCount;
    }

    private BruteForceIP getBruteForceIP(String ip) {
        BruteForceIP bruteForceIP = Redis.getConnection().get(getKey(ip), BruteForceIP.class);
        if (bruteForceIP == null)
            bruteForceIP = new BruteForceIP(0, new HashMap<>());
        return bruteForceIP;
    }

    private String getKey(String ip) {
        return "#bruteforce" + ip;
    }
}
