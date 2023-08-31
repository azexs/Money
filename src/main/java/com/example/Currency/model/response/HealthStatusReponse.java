package com.example.Currency.model.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"currency", "code", "mid", "bid", "ask", "date"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HealthStatusReponse {

    int eventsCount;
    String rabbitStatus;
    String nbpStatus;
}
