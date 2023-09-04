package com.example.Currency.entity.http.currencyexchange.tables.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CurrencyExchangeTableResponse {
    private String table;
    private String no;
    private String effectiveDate;
    private List<CurrencyExchangeTableRate> rates;
}
