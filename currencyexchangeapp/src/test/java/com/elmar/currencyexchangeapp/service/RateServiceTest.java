package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import com.elmar.currencyexchangeapp.util.KeyGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {

    @InjectMocks
    private RateService rateService;

    @Mock
    private ResponseHandler responseHandler;

    @Mock
    private KeyGenerator keyGenerator;

    @Mock
    private LastRateService lastRateService;

    @Mock
    private CurrencyService currencyService;

    @Test
    @DisplayName("Update method should call save methods when rate is changed")
    void updateShouldCallSaveMethods_whenRateIsChanged() throws JsonProcessingException {

        CurrencyRate currencyRate = buildDummyCurrencyRate();
        ExchangeDto exchangeDto = new ExchangeDto("USD", "EUR");

        when(responseHandler.getLatestRate(exchangeDto)).thenReturn(2.5);
        when(keyGenerator.buildKeyForLast(exchangeDto)).thenReturn("USDTOEURLAST");
        when(lastRateService.findByKey("USDTOEURLAST")).thenReturn("USDTOEUR2020010101");
        when(currencyService.findByKey("USDTOEUR2020010101")).thenReturn(currencyRate);

        rateService.updateCurrency(exchangeDto);

        verify(lastRateService, times(1)).save(any(), any());
        verify(currencyService, times(1)).save(any(), any());

    }

    private CurrencyRate buildDummyCurrencyRate(){
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setRate(1.5);
        currencyRate.setChartInterval5Min(new ArrayList<>());
        currencyRate.setChartInterval60Min(new ArrayList<>());
        currencyRate.setChartIntervalDaily(new ArrayList<>());
        return currencyRate;
    }
}
