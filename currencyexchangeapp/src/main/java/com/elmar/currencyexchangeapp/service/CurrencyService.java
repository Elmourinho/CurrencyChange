package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import com.elmar.currencyexchangeapp.repository.CurrencyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public void save(String key, CurrencyRate currencyRate){
        currencyRepository.save(key, currencyRate);
    }

    public CurrencyRate findByKey(String key){
        return currencyRepository.findByKey(key);
    }

}
