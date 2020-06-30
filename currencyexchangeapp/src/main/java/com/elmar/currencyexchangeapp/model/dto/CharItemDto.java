package com.elmar.currencyexchangeapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharItemDto implements Serializable {
    private String date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
}
