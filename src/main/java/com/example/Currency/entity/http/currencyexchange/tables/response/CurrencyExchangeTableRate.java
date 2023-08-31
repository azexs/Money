package com.example.Currency.entity.http.currencyexchange.tables.response;

import com.example.Currency.entity.http.currencyexchange.AbstractRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeTableRate extends AbstractRate {

    private String currency;
    private String code;
}
