package com.insession.securityproject.infrastructure.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {
    public static void withConnection(RedisConnection connection) {
        Jedis jedis = new Jedis("localhost", 6379);
        connection.commitConnection(jedis);
        jedis.close();
    }
}
