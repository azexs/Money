package com.example.Currency.model.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"title", "detail", "status", "instance"})
public class FailureResponse {
    private String title;
    private String detail;
    private Integer status;
    private String instance;
}
