package com.insession.securityproject.infrastructure.cache;

import redis.clients.jedis.Jedis;

public class Redis {
    private static Jedis getJedis() {
        String host = "localhost";
        if (System.getenv("DEPLOYED") != null)
            host = System.getenv("REDIS_URL");
        return new Jedis(host, 6379);
    }

    public static RedisConnection getConnection() {
        return new RedisConnection(getJedis());
    }
}
