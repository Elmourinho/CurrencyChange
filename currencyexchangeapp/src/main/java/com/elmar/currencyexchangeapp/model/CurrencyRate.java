package com.elmar.currencyexchangeapp.model;

import com.elmar.currencyexchangeapp.model.dto.CharItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class CurrencyRate implements Serializable {

    private Double rate;
    private List<CharItemDto> chartInterval5Min;
    private List<CharItemDto> chartInterval60Min;
    private List<CharItemDto> chartIntervalDaily;
}
