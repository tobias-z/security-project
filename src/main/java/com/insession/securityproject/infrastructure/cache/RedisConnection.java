package com.insession.securityproject.infrastructure.cache;

import redis.clients.jedis.Jedis;

@FunctionalInterface
public interface RedisConnection {
    void commitConnection(Jedis jedis);
}
