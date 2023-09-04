package com.example.Currency.entity.http.currencyexchange.tables.response;

import com.example.Currency.entity.http.currencyexchange.AbstractRate;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CurrencyExchangeTableRate extends AbstractRate {
    private String currency;
    private String code;
}
