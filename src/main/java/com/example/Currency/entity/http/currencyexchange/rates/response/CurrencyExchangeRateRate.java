package com.example.Currency.entity.http.currencyexchange.rates.response;

import com.example.Currency.entity.http.currencyexchange.AbstractRate;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CurrencyExchangeRateRate extends AbstractRate {
    public String no;
    public String effectiveDate;
}
