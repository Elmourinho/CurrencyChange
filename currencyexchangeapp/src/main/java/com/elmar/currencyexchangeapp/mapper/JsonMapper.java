package com.elmar.currencyexchangeapp.mapper;

import com.elmar.currencyexchangeapp.model.dto.CharItemDto;
import com.elmar.currencyexchangeapp.model.enumeration.ChartInterval;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Component
public class JsonMapper {

    public Double getRateFromJson(String jsonInput) throws JsonProcessingException {

        JsonNode rateNode = new ObjectMapper().readTree(jsonInput);
        return rateNode.get("Realtime Currency Exchange Rate").get("5. Exchange Rate").asDouble();
    }

    public List<CharItemDto> getChartDataFromJson(String jsonInput, ChartInterval chartInterval) throws JsonProcessingException {

        List<CharItemDto> resultList = new ArrayList<>();
        String fieldName = String.format("Time Series FX (%s)", chartInterval.toString());

        JsonNode chartNode = new ObjectMapper().readTree(jsonInput);
        JsonNode js = chartNode.findValues(fieldName).get(0);
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = js.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            JsonNode valueNode = field.getValue();

            resultList.add(CharItemDto.builder()
                    .date(field.getKey())
                    .open(valueNode.get("1. open").asDouble())
                    .high(valueNode.get("2. high").asDouble())
                    .low(valueNode.get("3. low").asDouble())
                    .close(valueNode.get("4. close").asDouble())
                    .build());
        }
        return resultList;
    }
}
