package com.example.Currency.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"currency", "code", "mid", "bid", "ask", "date"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RateResponse {
    private String currency;
    private String code;
    private Double mid;
    private Double bid;
    private Double ask;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
