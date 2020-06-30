package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import com.elmar.currencyexchangeapp.repository.CurrencyRepository;
import com.elmar.currencyexchangeapp.repository.LastRateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LastRateService {

    private final LastRateRepository lastRateRepository;

    public void save(String key, String rate){
        lastRateRepository.save(key, rate);
    }

    public String findByKey(String key){
        return lastRateRepository.findByKey(key);
    }
}
