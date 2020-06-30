package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.repository.LastRateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LastRateServiceTest {

    @InjectMocks
    private LastRateService lastRateService;

    @Mock
    private LastRateRepository lastRateRepository;

    @Test
    @DisplayName("Save should call once")
    void saveShouldCallOnce() {

        String key = "KEY";
        String value = "VALUE";

        doNothing().when(lastRateRepository).save(isA(String.class), isA(String.class));
        lastRateService.save(key, value);

        verify(lastRateRepository, times(1)).save(any(), any());

    }

    @Test
    @DisplayName("FindByKey should call once")
    void findByKeyShouldCallOnce() {

        String key = "KEY";
        String value = "VALUE";

        when(lastRateRepository.findByKey(key)).thenReturn(value);
        lastRateService.findByKey(key);

        verify(lastRateRepository, times(1)).findByKey(any());

    }
}
