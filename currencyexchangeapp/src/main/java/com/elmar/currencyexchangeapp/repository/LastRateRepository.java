package com.elmar.currencyexchangeapp.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class LastRateRepository {

    private RedisTemplate<String, String> redisTemplate;
    private HashOperations hashOperations;

    private static final String KEY = "lastRate";

    public LastRateRepository(@Qualifier("lastRate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(String key, String rate) {
        hashOperations.put(KEY, key, rate);
    }

    public String findByKey(String key) {
        if (hashOperations.get(KEY, key) == null) {
            return null;
        }
        return String.valueOf(hashOperations.get(KEY, key));
    }
}
