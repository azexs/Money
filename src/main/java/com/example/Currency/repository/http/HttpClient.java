package com.example.Currency.repository.http;

import com.example.Currency.entity.http.currencyexchange.rates.response.CurrencyExchangeRateResponse;
import com.example.Currency.entity.http.currencyexchange.tables.response.CurrencyExchangeTableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class HttpClient {

    private final RestTemplate restTemplate;

    public HttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<CurrencyExchangeTableResponse>> getExchangeTableList(String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CurrencyExchangeTableResponse>>() {
                }
        );
    }

    public ResponseEntity<CurrencyExchangeRateResponse> getExchangeRate(String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                CurrencyExchangeRateResponse.class
        );
    }
}
