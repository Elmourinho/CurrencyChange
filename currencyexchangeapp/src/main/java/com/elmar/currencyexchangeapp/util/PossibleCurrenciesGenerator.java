package com.elmar.currencyexchangeapp.util;

import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PossibleCurrenciesGenerator {

    public List<ExchangeDto> generatePossibleCurrencyPairs(String supportedCurrencies) {
        List<ExchangeDto> resultList = new ArrayList<>();
        String[] currencyList= supportedCurrencies.split(",");
        for (int i = 0; i < currencyList.length; i++) {
            for (int j = 0; j < currencyList.length; j++) {
                if (i == j) continue;
                ExchangeDto exchangeDto = new ExchangeDto(currencyList[i], currencyList[j]);
                resultList.add(exchangeDto);
            }
        }
        return resultList;
    }
}
