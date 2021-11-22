package com.insession.securityproject.infrastructure.cache.saved;

import java.time.LocalDateTime;

public class BruteForceCount {
    private final String username;
    private Integer count;
    private LocalDateTime lastDate;

    public BruteForceCount(String username, Integer count, LocalDateTime lastDate) {
        this.username = username;
        this.count = count;
        this.lastDate = lastDate;
    }

    public String getUsername() {
        return username;
    }

    public Integer getCount() {
        return count;
    }

    public void incrementCount() {
        this.count++;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDateTime lastDate) {
        this.lastDate = lastDate;
    }
}
