package com.elmar.currencyexchangeapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExchangeDto {
    private String from;
    private String to;
}
