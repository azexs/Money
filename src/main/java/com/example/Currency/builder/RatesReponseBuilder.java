package com.example.Currency.builder;


import com.example.Currency.entity.http.currencyexchange.rates.response.CurrencyExchangeRateResponse;
import com.example.Currency.entity.http.currencyexchange.tables.response.CurrencyExchangeTableRate;
import com.example.Currency.model.response.RateResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RatesReponseBuilder {


    public List<RateResponse> buildResponse(Map<String, CurrencyExchangeTableRate> combinedRates, String effectiveDate) {
        return combinedRates.values().stream().map(rate -> buildResponse(rate, effectiveDate)).collect(Collectors.toList());
    }

    public RateResponse buildResponse(CurrencyExchangeTableRate rate, String effectiveDate) {
        return RateResponse.builder()
                .currency(rate.getCurrency())
                .code(rate.getCode())
                .date(LocalDate.parse(effectiveDate))
                .ask(rate.getAsk())
                .bid(rate.getBid())
                .mid(rate.getMid())
                .build();
    }

    public RateResponse buildResponse(CurrencyExchangeRateResponse rate) {
        return RateResponse.builder()
                .currency(rate.getCurrency())
                .code(rate.getCode())
                .date(LocalDate.parse(rate.getRates().get(0).getEffectiveDate()))
                .ask(rate.getRates().get(0).getAsk())
                .bid(rate.getRates().get(0).getBid())
                .mid(rate.getRates().get(0).getMid())
                .build();
    }
}
