package com.insession.securityproject.infrastructure.cache;

import redis.clients.jedis.Jedis;

public class Redis {
    private static Jedis getJedis() {
        String host = "localhost";
        boolean isDeployed = System.getenv("DEPLOYED") != null;
        if (isDeployed)
            host = System.getenv("REDIS_URL");

        Jedis jedis = new Jedis(host, 6379);
        if (isDeployed)
            jedis.auth(System.getenv("REDIS_PASSWORD"));
        return jedis;
    }

    public static RedisConnection getConnection() {
        return new RedisConnection(getJedis());
    }
}
