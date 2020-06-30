package com.elmar.currencyexchangeapp.util;

import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class KeyGenerator {

    Format formatter = new SimpleDateFormat("yyyyMMddhhmm");

    public String buildKey(ExchangeDto exchangeDto) {
        return String.format("%sTO%s%s", exchangeDto.getFrom(), exchangeDto.getTo(), formatter.format(new Date()));
    }

    public String buildKeyForLast(ExchangeDto exchangeDto) {
        return String.format("%sTO%sLAST", exchangeDto.getFrom(), exchangeDto.getTo());
    }
}
