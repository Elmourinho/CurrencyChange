package com.elmar.currencyexchangeapp.configuration;

import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueConfig {

    @Bean
    public BlockingQueue<ExchangeDto> blockingQueue() {
        return new LinkedBlockingQueue<>();
    }

}
