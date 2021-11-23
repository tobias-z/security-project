package com.insession.securityproject.infrastructure.cache.saved;

import com.insession.securityproject.api.services.BruteForceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BruteForceIP {
    private Integer fullCount;
    private LocalDateTime lastDate;
    private final Map<String, BruteForceCount> bruteForceCounts;

    public BruteForceIP(Integer fullCount, Map<String, BruteForceCount> bruteForceCounts, LocalDateTime lastDate) {
        this.fullCount = fullCount;
        this.bruteForceCounts = bruteForceCounts;
        this.lastDate = lastDate;
    }

    public Integer getFullCount() {
        return fullCount;
    }

    public Map<String, BruteForceCount> getBruteForceCount() {
        return bruteForceCounts;
    }

    public void incrementCount() {
        this.fullCount++;
    }

    public void resetCount() {
        this.fullCount = 1;
    }

    public void updateLastDate(int banTimeSeconds) {
        this.lastDate = LocalDateTime.now().plusSeconds(banTimeSeconds);
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }
}
