package com.example.Currency.entity.http.currencyexchange.rates.response;

import com.example.Currency.entity.http.currencyexchange.AbstractRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyExchangeRateRate extends AbstractRate {

    public String no;
    public String effectiveDate;
}
