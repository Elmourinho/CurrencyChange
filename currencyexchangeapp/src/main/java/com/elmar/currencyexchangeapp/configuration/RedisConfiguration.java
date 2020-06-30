package com.elmar.currencyexchangeapp.configuration;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean(name = "currency")
    RedisTemplate<String, CurrencyRate> currencyRedisTemplate() {
        RedisTemplate<String, CurrencyRate> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean(name = "lastRate")
    RedisTemplate<String, String> lastRateRedisTemplate() {
        RedisTemplate<String, String> rateRedisTemplate = new RedisTemplate<>();
        rateRedisTemplate.setConnectionFactory(jedisConnectionFactory());
        return rateRedisTemplate;
    }


}
