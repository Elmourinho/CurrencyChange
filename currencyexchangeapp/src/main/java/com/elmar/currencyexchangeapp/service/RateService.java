package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.configuration.QueueConfig;
import com.elmar.currencyexchangeapp.model.CurrencyRate;
import com.elmar.currencyexchangeapp.model.dto.CharItemDto;
import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import com.elmar.currencyexchangeapp.model.enumeration.ChartInterval;
import com.elmar.currencyexchangeapp.util.KeyGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RateService {

    private final CurrencyService currencyService;
    private final LastRateService lastRateService;
    private final QueueConfig queueConfig;
    private final KeyGenerator keyGenerator;
    private final ResponseHandler responseHandler;
    private LocalDateTime lastUpdated;

    public RateService(CurrencyService currencyService, LastRateService lastRateService,
                       QueueConfig queueConfig, KeyGenerator keyGenerator, ResponseHandler responseHandler) {
        this.currencyService = currencyService;
        this.lastRateService = lastRateService;
        this.queueConfig = queueConfig;
        this.keyGenerator = keyGenerator;
        this.responseHandler = responseHandler;
        lastUpdated = LocalDateTime.now();
    }


    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    private void scheduled() throws InterruptedException {
        if (!queueConfig.blockingQueue().isEmpty()) {
            ExchangeDto exchangeDto = queueConfig.blockingQueue().take();
            new Thread(() -> {
                try {
                    updateCurrency(exchangeDto);
                } catch (Exception e) {
                    queueConfig.blockingQueue().add(exchangeDto);
                }
            }).start();
        }
    }

    public void updateRate(ExchangeDto exchangeDto) {
        queueConfig.blockingQueue().add(exchangeDto);
    }

    public CurrencyRate getLastRate(ExchangeDto exchangeDto) {
        String lastKey = keyGenerator.buildKeyForLast(exchangeDto);
        String lastRateValue = lastRateService.findByKey(lastKey);
        if (lastRateValue == null) {
            return null;
        }
        return currencyService.findByKey(lastRateValue);
    }


    void updateCurrency(ExchangeDto exchangeDto) throws JsonProcessingException {

        Double latestRate = responseHandler.getLatestRate(exchangeDto);
        CurrencyRate lastSavedCurrency = getLastRate(exchangeDto);

        if (lastSavedCurrency == null || !lastSavedCurrency.getRate().equals(latestRate)) {
            String key = keyGenerator.buildKey(exchangeDto);
            String lastKey = keyGenerator.buildKeyForLast(exchangeDto);

            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setRate(latestRate);

            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(lastUpdated, now);
            long diff = Math.abs(duration.toMinutes());
            if(diff > 5){
                List<CharItemDto> interval5Min = responseHandler.getChartData(exchangeDto, ChartInterval.MIN5);
                currencyRate.setChartInterval5Min(interval5Min);
            }
            if(diff > 60){
                List<CharItemDto> interval60Min = responseHandler.getChartData(exchangeDto, ChartInterval.MIN60);
                currencyRate.setChartInterval60Min(interval60Min);
            }
            if(diff > 24*60){
                List<CharItemDto> intervalDaily = responseHandler.getChartData(exchangeDto, ChartInterval.DAILY);
                currencyRate.setChartIntervalDaily(intervalDaily);
            }

            lastRateService.save(lastKey, key);
            currencyService.save(key, currencyRate);
            lastUpdated = LocalDateTime.now();

        }
    }

}
