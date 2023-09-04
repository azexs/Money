package com.example.Currency.controller;

import com.example.Currency.model.response.HealthStatusResponse;
import com.example.Currency.utils.SharedData;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private static final String NBP_TEST_URL = "http://api.nbp.pl/api/exchangerates/tables/A";

    private final SharedData sharedData;
    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    @GetMapping("/health")
    public HealthStatusResponse getHealthStatus() {
        return HealthStatusResponse.builder()
                .eventsCount(sharedData.getRequestCount())
                .rabbitStatus(checkRabbitStatus())
                .nbpStatus(checkNbpStatus())
                .build();
    }

    public String checkNbpStatus() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(NBP_TEST_URL, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "UP";
            } else {
                return "Error:" + response.getStatusCode();
            }
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }

    public String checkRabbitStatus() {
        try {
            rabbitTemplate.execute(connection -> true);
            return "UP";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
