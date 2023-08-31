package com.example.Currency.base.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ApplicationConfig {

    @Bean(name = "nbpRabbitTemplate")
    RestTemplate rabbitTemplate() {
        return new RestTemplate();
    }
}