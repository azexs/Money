package com.example.Currency.entity.http.currencyexchange.rates.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class CurrencyExchangeRateResponse {
    public String table;
    public String currency;
    public String code;
    public ArrayList<CurrencyExchangeRateRate> rates = new ArrayList<>();
}
