package com.insession.securityproject.infrastructure.cache;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RedisConnection {
    private final Gson GSON = new Gson();
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

    public RedisConnection remove(String... keys) {
        jedis.del(keys);
        return this;
    }

    public RedisConnection removeAll() {
        jedis.flushDB();
        return this;
    }

    public void close() {
        this.jedis.close();
    }
}
