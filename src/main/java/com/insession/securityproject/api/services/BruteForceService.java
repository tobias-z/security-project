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
    private final Integer banTimeSeconds;

    public BruteForceService(Integer maxTries, Integer userTries, Integer banTimeSeconds) {
        this.maxTries = maxTries;
        this.userTries = userTries;
        this.banTimeSeconds = banTimeSeconds;
    }

    public void resetUser(String ip) {
        Redis.getConnection()
                .put(getKey(ip), getNewBruteForceIP());
    }

    public void handleBruteForce(String ip, String username) throws BruteForceException {
        BruteForceIP bruteForceIP = getBruteForceIP(ip);
        BruteForceCount bruteForceCount = getBruteForceCount(username, bruteForceIP);
        bruteForceCount.incrementCount();
        bruteForceIP.incrementCount();

        boolean isBanned = isBanned(bruteForceIP, bruteForceCount);
        bruteForceCount.updateLastDate(banTimeSeconds);
        bruteForceIP.updateLastDate(banTimeSeconds);
        Redis.getConnection().put(getKey(ip), bruteForceIP).close();

        if (isBanned)
            throw new BruteForceException("Not allowed");
    }

    private boolean isBanned(BruteForceIP bruteForceIP, BruteForceCount bruteForceCount) {
        if (isBannedOfUser(bruteForceCount))
            return true;

        if (isIPBanned(bruteForceIP))
            return true;

        int count = 0;
        for (Map.Entry<String, BruteForceCount> entry : bruteForceIP.getBruteForceCount().entrySet()) {
            if (entry.getValue().getCount() > userTries) count++;
        }

        return count >= 3;
    }

    private boolean isBannedOfUser(BruteForceCount bruteForceCount) {
        boolean timeHasRunOut = bruteForceCount.getLastDate().isBefore(LocalDateTime.now());
        if (timeHasRunOut) {
            bruteForceCount.resetCount();
        }

        return bruteForceCount.getCount() > userTries;
    }

    private boolean isIPBanned(BruteForceIP bruteForceIP) {
        boolean timeHasRunOut = bruteForceIP.getLastDate().isBefore(LocalDateTime.now());
        if (timeHasRunOut) {
            bruteForceIP.resetCount();
        }

        return bruteForceIP.getFullCount() > maxTries;
    }

    private BruteForceCount getBruteForceCount(String username, BruteForceIP bruteForceIP) {
        BruteForceCount bruteForceCount = bruteForceIP.getBruteForceCount().get(username);
        if (bruteForceCount != null)
            return bruteForceCount;

        bruteForceCount = new BruteForceCount(username, 0, LocalDateTime.now().plusSeconds(banTimeSeconds));
        bruteForceIP.getBruteForceCount().put(username, bruteForceCount);
        return bruteForceCount;
    }

    private BruteForceIP getBruteForceIP(String ip) {
        BruteForceIP bruteForceIP = Redis.getConnection().get(getKey(ip), BruteForceIP.class);
        if (bruteForceIP == null)
            bruteForceIP = getNewBruteForceIP();
        return bruteForceIP;
    }

    private String getKey(String ip) {
        return "#bruteforce" + ip;
    }

    private BruteForceIP getNewBruteForceIP() {
        return new BruteForceIP(0, new HashMap<>(), LocalDateTime.now().plusSeconds(banTimeSeconds));
    }
}
