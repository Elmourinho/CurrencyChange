package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.configuration.ApplicationProperties;
import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import com.elmar.currencyexchangeapp.util.PossibleCurrenciesGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyDataUpdater {

    private final ApplicationProperties applicationProperties;
    private final PossibleCurrenciesGenerator possibleCurrenciesGenerator;
    private final CurrencySaveHandler currencySaveHandler;
    private List<ExchangeDto> supportedCurrencies;

    public CurrencyDataUpdater(ApplicationProperties applicationProperties, PossibleCurrenciesGenerator possibleCurrenciesGenerator,
                               CurrencySaveHandler currencySaveHandler) {
        this.applicationProperties = applicationProperties;
        this.possibleCurrenciesGenerator = possibleCurrenciesGenerator;
        this.currencySaveHandler = currencySaveHandler;
        supportedCurrencies = possibleCurrenciesGenerator.
                generatePossibleCurrencyPairs(applicationProperties.getPublicApi().getSupportedCurrencies());
    }

    @Scheduled(initialDelay = 4*60*1000L, fixedDelay = 5*60*1000L)
    public void updateRates() {
        supportedCurrencies.forEach(currency -> {
            try {
                currencySaveHandler.saveCurrencyRate(currency);
                Thread.sleep(12*1000L);
            } catch (JsonProcessingException | InterruptedException ex) {
                // As public API can send 5 responses per minute,
                // by this way I tried to avoid possible issues
            }
        });
    }

    @Scheduled(initialDelay = 7*60*1000L, fixedDelay = 5*60*1000L)
    public void update5MinIntervals() {
        supportedCurrencies.forEach(currency -> {
            try {
                currencySaveHandler.saveCurrencyChartDataFor5MinInterval(currency);
                Thread.sleep(12*1000L);
            } catch (JsonProcessingException | InterruptedException ex) {
            }
        });
    }

    @Scheduled(initialDelay = 10*60*1000L, fixedDelay = 60*60*1000L)
    public void update60MinIntervals() {
        supportedCurrencies.forEach(currency -> {
            try {
                currencySaveHandler.saveCurrencyRate(currency);
                Thread.sleep(12*1000L);
            } catch (JsonProcessingException | InterruptedException ex) {
            }
        });
    }

    @Scheduled(initialDelay = 13*60*1000L, fixedDelay = 24*60*60*1000L)
    public void updateDailyIntervals() {
        supportedCurrencies.forEach(currency -> {
            try {
                currencySaveHandler.saveCurrencyRate(currency);
                Thread.sleep(12*1000L);
            } catch (JsonProcessingException | InterruptedException ex) {
            }
        });
    }
}
