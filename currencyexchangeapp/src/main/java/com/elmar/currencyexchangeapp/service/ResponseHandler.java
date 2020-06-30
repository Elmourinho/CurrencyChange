package com.elmar.currencyexchangeapp.service;

import com.elmar.currencyexchangeapp.configuration.ApplicationProperties;
import com.elmar.currencyexchangeapp.mapper.JsonMapper;
import com.elmar.currencyexchangeapp.model.dto.CharItemDto;
import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import com.elmar.currencyexchangeapp.model.enumeration.ChartInterval;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@AllArgsConstructor
public class ResponseHandler {

    private final RestTemplate restTemplate;
    private final JsonMapper jsonMapper;
    private final ApplicationProperties applicationProperties;

    public Double getLatestRate(ExchangeDto exchangeDto) throws JsonProcessingException {

        ApplicationProperties.PublicApi publicApi = applicationProperties.getPublicApi();

        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(publicApi.getUrl())
                .queryParam("function", "CURRENCY_EXCHANGE_RATE")
                .queryParam("from_currency", exchangeDto.getFrom())
                .queryParam("to_currency", exchangeDto.getTo())
                .queryParam("apikey", publicApi.getKey());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        return jsonMapper.getRateFromJson(response.getBody());

    }

    public List<CharItemDto> getChartData(ExchangeDto exchangeDto, ChartInterval chartInterval) throws JsonProcessingException {

        String uri = buildChartUri(exchangeDto, chartInterval);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class);
        return jsonMapper.getChartDataFromJson(response.getBody(), chartInterval);

    }

    private String buildChartUri(ExchangeDto exchangeDto, ChartInterval chartInterval) {
        ApplicationProperties.PublicApi publicApi = applicationProperties.getPublicApi();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(publicApi.getUrl())
                .queryParam("from_symbol", exchangeDto.getFrom())
                .queryParam("to_symbol", exchangeDto.getTo())
                .queryParam("apikey", publicApi.getKey());

        if (chartInterval == ChartInterval.DAILY) {
            builder.queryParam("function", "FX_DAILY");
        } else {
            builder.queryParam("function", "FX_INTRADAY");
            builder.queryParam("interval", chartInterval.toString());
        }

        return builder.toUriString();
    }

}
