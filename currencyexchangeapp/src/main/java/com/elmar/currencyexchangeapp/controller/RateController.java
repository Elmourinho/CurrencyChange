package com.elmar.currencyexchangeapp.controller;

import com.elmar.currencyexchangeapp.model.CurrencyRate;
import com.elmar.currencyexchangeapp.model.dto.ExchangeDto;
import com.elmar.currencyexchangeapp.service.RateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;

    @PutMapping
    public void update(@RequestParam String from, @RequestParam String to) {
        rateService.updateRate(new ExchangeDto(from, to));
    }

    @GetMapping
    public CurrencyRate getLastRate(@RequestParam String from, @RequestParam String to) {
        return rateService.getLastRate(new ExchangeDto(from.toUpperCase(), to.toUpperCase()));
    }

}
