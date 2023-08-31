package com.example.Currency.service.impl;

import com.example.Currency.builder.RatesReponseBuilder;
import com.example.Currency.entity.http.currencyexchange.AbstractRate;
import com.example.Currency.entity.http.currencyexchange.rates.response.CurrencyExchangeRateResponse;
import com.example.Currency.entity.http.currencyexchange.tables.response.CurrencyExchangeTableRate;
import com.example.Currency.entity.http.currencyexchange.tables.response.CurrencyExchangeTableResponse;
import com.example.Currency.commons.types.TableType;
import com.example.Currency.model.response.RateResponse;
import com.example.Currency.repository.http.HttpClient;
import com.example.Currency.service.IRatesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatesService implements IRatesService {


    private static final String TABLE_URL_ALL = "%s/tables/%s";
    private static final String TABLE_URL_DATE = "%s/tables/%s/%s/";
    private static final String RATE_URL_CODE = "%s/rates/%s/%s/";

    @Value("${external.exchangerates.api.url}")
    private String url;

    private final RatesReponseBuilder ratesReponseBuilder;
    private final HttpClient httpClient;

    public RatesService(RatesReponseBuilder ratesReponseBuilder,
                        HttpClient httpClient) {
        this.ratesReponseBuilder = ratesReponseBuilder;
        this.httpClient = httpClient;
    }

    @Override
    public List<RateResponse> getExchangeRatesList() {
        return getCombinedRates(String.format(TABLE_URL_ALL, url, "%s"));
    }

    @Override
    public List<RateResponse> getExchangeRatesList(LocalDate date) {
        return getCombinedRates(String.format(TABLE_URL_DATE, url, "%s", date));
    }

    @Override
    public RateResponse getExchangeRate(String currencyCode) {
        return ratesReponseBuilder.buildResponse(fetchExchangeRateForCode(currencyCode));
    }

    @Override
    public RateResponse updateExchangeRates(String currencyCode) {
        CurrencyExchangeRateResponse rate = fetchExchangeRateForCode(currencyCode);
        if (rate.getRates().isEmpty()) {
            return null;
        }
        //rabbitTemplate.convertAndSend(exchange, routingKey, message);

        return ratesReponseBuilder.buildResponse(rate);
    }

    private CurrencyExchangeRateResponse fetchExchangeRateForCode(String code) {
        CurrencyExchangeRateResponse rateResponse = null;


        for (TableType tableType : TableType.values()) {
            try {
                String targetUrl = String.format(RATE_URL_CODE, url, tableType, code);
                ResponseEntity<CurrencyExchangeRateResponse> response = httpClient.getExchangeRate(targetUrl);
                if (response.getBody() != null && !response.getBody().getRates().isEmpty()) {
                    rateResponse = response.getBody();
                    mergeRates(rateResponse.getRates().get(0), response.getBody().getRates().get(0));
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                    throw e;
                }
            }
        }

        if (rateResponse == null) {
            throw new ResourceNotFoundException();
        }

        return rateResponse;
    }

    private List<RateResponse> getCombinedRates(String urlPattern) {
        Map<String, CurrencyExchangeTableRate> combinedRates = new HashMap<>();
        String effectiveDate = null;
        for (TableType tableType : TableType.values()) {
            String targetUrl = String.format(urlPattern, tableType);
            try {
                ResponseEntity<List<CurrencyExchangeTableResponse>> response = httpClient.getExchangeTableList(targetUrl);
                if (response.getBody() != null) {
                    for (CurrencyExchangeTableRate rate : response.getBody().get(0).getRates()) {
                        combinedRates.merge(rate.getCode(), rate, this::mergeRates);
                    }
                    effectiveDate = response.getBody().get(0).getEffectiveDate();
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
                    throw e;
                }
            }
        }
        return ratesReponseBuilder.buildResponse(combinedRates, effectiveDate);
    }

    private <T extends AbstractRate> T mergeRates(T rate1, T rate2) {
        if (rate2.getMid() != null) {
            rate1.setMid(rate2.getMid());
        }
        if (rate2.getAsk() != null) {
            rate1.setAsk(rate2.getAsk());
        }
        if (rate2.getBid() != null) {
            rate1.setBid(rate2.getBid());
        }
        return rate1;
    }
}

