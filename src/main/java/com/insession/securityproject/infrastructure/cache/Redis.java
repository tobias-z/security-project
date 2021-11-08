package com.insession.securityproject.infrastructure.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Redis {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Jedis getJedis() {
        return new Jedis("localhost", 6379);
    }

    public static void put(String key, Object toCache) {
        try (Jedis jedis = getJedis()) {
            jedis.set(key, new Gson().toJson(toCache));
        }
    }

    public static <T> T get(String key, Class<T> clazz) {
        try (Jedis jedis = getJedis()) {
            String value = jedis.get(key);
            if (value == null)
                return null;
            return GSON.fromJson(value, clazz);
        }
    }
}
