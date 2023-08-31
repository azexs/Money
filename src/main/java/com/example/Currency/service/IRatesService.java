package com.example.Currency.service;


import com.example.Currency.model.response.RateResponse;

import java.time.LocalDate;
import java.util.List;

public interface IRatesService {

    List<RateResponse> getExchangeRatesList();

    List<RateResponse> getExchangeRatesList(LocalDate date);

    RateResponse getExchangeRate(String code);

    RateResponse updateExchangeRates(String currencyCode);
}
