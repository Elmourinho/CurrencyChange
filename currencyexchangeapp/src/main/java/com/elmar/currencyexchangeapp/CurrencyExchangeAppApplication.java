package com.elmar.currencyexchangeapp;

import com.elmar.currencyexchangeapp.configuration.ApplicationProperties;
import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import com.elmar.currencyexchangeapp.service.CurrencySaveHandler;
import com.elmar.currencyexchangeapp.util.PossibleCurrenciesGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({ApplicationProperties.class})
@AllArgsConstructor
public class CurrencyExchangeAppApplication {

    private final ApplicationProperties applicationProperties;
    private final PossibleCurrenciesGenerator possibleCurrenciesGenerator;
    private final CurrencySaveHandler currencySaveHandler;

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeAppApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialData() {
        String supportedCurrenciesString = applicationProperties.getPublicApi().getSupportedCurrencies();
        List<ExchangeDto> supportedCurrencies = possibleCurrenciesGenerator.generatePossibleCurrencyPairs(supportedCurrenciesString);
        supportedCurrencies.forEach(currency -> {
            try {
                currencySaveHandler.saveCurrencyRate(currency);
                currencySaveHandler.saveCurrencyChartDataFor5MinInterval(currency);
                currencySaveHandler.saveCurrencyChartDataFor60MinInterval(currency);
                currencySaveHandler.saveCurrencyChartDataForDailyInterval(currency);
                Thread.sleep(60*1000L);
            } catch (JsonProcessingException | InterruptedException ex) {
                // As public API can send 5 responses per minute,
                // by this way I tried to avoid possible issues
            }
        });

    }

}

