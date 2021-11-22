package com.insession.securityproject.infrastructure.cache.saved;

import com.insession.securityproject.api.services.BruteForceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BruteForceIP {
    private Integer fullCount;
    private final Map<String, BruteForceCount> bruteForceCounts;

    public BruteForceIP(Integer fullCount, Map<String, BruteForceCount> bruteForceCounts) {
        this.fullCount = fullCount;
        this.bruteForceCounts = bruteForceCounts;
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
}
