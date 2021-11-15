package com.insession.securityproject.infrastructure.cache;

import redis.clients.jedis.Jedis;

public class Redis {
    private static Jedis getJedis() {
        return new Jedis("redis", 6379);
    }

    public static RedisConnection getConnection() {
        return new RedisConnection(getJedis());
    }
}
