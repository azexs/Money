package com.example.Currency.controller;


import com.example.Currency.model.response.RateResponse;
import com.example.Currency.service.IRatesService;
import com.example.Currency.utils.SharedData;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@RestController
@Validated
public class RatesController {

    private final IRatesService ratesService;
    private final SharedData sharedData;

    public RatesController(@Qualifier("ratesCacheService") IRatesService ratesService, SharedData sharedData) {
        this.ratesService = ratesService;
        this.sharedData = sharedData;
    }


    @GetMapping("/rates")
    public List<RateResponse> getExchangeRatesList(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date) {
        if (date.isPresent()) {
            return ratesService.getExchangeRatesList(date.get());
        }
        return ratesService.getExchangeRatesList();
    }

    @GetMapping(value = "/rates/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RateResponse getExchangeRate(
            @PathVariable @NotBlank(message = "ID can not be blank") String id
    ) {
        return ratesService.getExchangeRate(id);
    }

    @PostMapping(value = "/refresh-cache/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RateResponse updateExchangeRate(
            @PathVariable @NotBlank(message = "ID can not be blank") String id
    ) {
        RateResponse rateResponse = ratesService.updateExchangeRates(id);
        sharedData.increment();
        return rateResponse;

    }
}