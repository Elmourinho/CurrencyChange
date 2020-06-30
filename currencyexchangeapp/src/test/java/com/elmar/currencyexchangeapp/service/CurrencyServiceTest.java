package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import com.elmar.currencyexchangeapp.repository.CurrencyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepository currencyRepository;

    @Test
    @DisplayName("Save should call once")
    void saveShouldCallOnce() {

        String key = "KEY";
        CurrencyRate currencyRate = buildDummyCurrencyRate();

        doNothing().when(currencyRepository).save(isA(String.class), isA(CurrencyRate.class));
        currencyService.save(key, currencyRate);

        verify(currencyRepository, times(1)).save(any(), any());

    }

    @Test
    @DisplayName("FindByKey should call once")
    void findByKeyShouldCallOnce() {

        String key = "KEY";
        CurrencyRate currencyRate = buildDummyCurrencyRate();

        when(currencyRepository.findByKey(key)).thenReturn(currencyRate);
        currencyService.findByKey(key);

        verify(currencyRepository, times(1)).findByKey(any());

    }

    private CurrencyRate buildDummyCurrencyRate() {
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setRate(1.5);
        currencyRate.setChartInterval5Min(new ArrayList<>());
        currencyRate.setChartInterval60Min(new ArrayList<>());
        currencyRate.setChartIntervalDaily(new ArrayList<>());
        return currencyRate;
    }
}
