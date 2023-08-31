package com.example.Currency.entity.http.currencyexchange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractRate {
    private Double mid;
    private Double bid;
    private Double ask;
}
