package com.insession.securityproject.infrastructure.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;

public class RedisConnection {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Jedis jedis;

    public RedisConnection(Jedis jedis) {
        this.jedis = jedis;
    }

    public RedisConnection put(String key, Object toCache) {
        jedis.set(key, GSON.toJson(toCache));
        return this;
    }

    public <T> T get(String key, Class<T> clazz) {
        String value = jedis.get(key);
        if (value == null)
            return null;
        close();
        return GSON.fromJson(value, clazz);
    }

    public RedisConnection pop(String key) {
        jedis.del(key);
        return this;
    }

    public void close() {
        this.jedis.close();
    }
}
