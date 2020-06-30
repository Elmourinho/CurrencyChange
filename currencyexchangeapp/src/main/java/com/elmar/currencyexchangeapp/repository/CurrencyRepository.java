package com.elmar.currencyexchangeapp.repository;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class CurrencyRepository {

    private RedisTemplate<String, CurrencyRate> redisTemplate;
    private HashOperations hashOperations;

    private static final String KEY = "currency";

    public CurrencyRepository(@Qualifier("currency") RedisTemplate<String, CurrencyRate> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(String key, CurrencyRate rate) {
        hashOperations.put(KEY, key, rate);
    }

    public CurrencyRate findByKey(String key){
        return (CurrencyRate) hashOperations.get(KEY, key);
    }
}
