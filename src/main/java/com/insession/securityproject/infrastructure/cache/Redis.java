package com.insession.securityproject.infrastructure.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {
    public static Jedis getJedis() {
        return new Jedis("localhost", 6379);
    }
}
