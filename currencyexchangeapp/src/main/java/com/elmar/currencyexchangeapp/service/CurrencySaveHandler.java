package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import com.elmar.currencyexchangeapp.model.dto.CharItemDto;
import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import com.elmar.currencyexchangeapp.model.enumeration.ChartInterval;
import com.elmar.currencyexchangeapp.util.KeyGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CurrencySaveHandler {

    private final CurrencyService currencyService;
    private final LastRateService lastRateService;
    private final KeyGenerator keyGenerator;
    private final ResponseHandler responseHandler;

    public void saveCurrencyRate(ExchangeDto exchangeDto) throws JsonProcessingException {
        Double latestRate = responseHandler.getLatestRate(exchangeDto);
        String key = keyGenerator.buildKey(exchangeDto);
        String lastKey = keyGenerator.buildKeyForLast(exchangeDto);

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setRate(latestRate);

        lastRateService.save(lastKey, key);
        currencyService.save(key, currencyRate);
    }

    public void saveCurrencyChartDataFor5MinInterval(ExchangeDto exchangeDto) throws JsonProcessingException {

        String keyLast = keyGenerator.buildKeyForLast(exchangeDto);
        String key = lastRateService.findByKey(keyLast);

        CurrencyRate currencyRate = currencyService.findByKey(key);
        List<CharItemDto> interval5Min = responseHandler.getChartData(exchangeDto, ChartInterval.MIN5);

        currencyRate.setChartInterval5Min(interval5Min);
        currencyService.save(key, currencyRate);

    }

    public void saveCurrencyChartDataFor60MinInterval(ExchangeDto exchangeDto) throws JsonProcessingException {

        String keyLast = keyGenerator.buildKeyForLast(exchangeDto);
        String key = lastRateService.findByKey(keyLast);

        CurrencyRate currencyRate = currencyService.findByKey(key);
        List<CharItemDto> interval60Min = responseHandler.getChartData(exchangeDto, ChartInterval.MIN60);

        currencyRate.setChartInterval60Min(interval60Min);
        currencyService.save(key, currencyRate);

    }

    public void saveCurrencyChartDataForDailyInterval(ExchangeDto exchangeDto) throws JsonProcessingException {

        String keyLast = keyGenerator.buildKeyForLast(exchangeDto);
        String key = lastRateService.findByKey(keyLast);

        CurrencyRate currencyRate = currencyService.findByKey(key);

        List<CharItemDto> intervalDaily = responseHandler.getChartData(exchangeDto, ChartInterval.DAILY);

        currencyRate.setChartIntervalDaily(intervalDaily);
        currencyService.save(key, currencyRate);

    }
}
