package com.example.Currency.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SharedData {
    private final AtomicInteger requestCount = new AtomicInteger(0);

    public void increment() {
        requestCount.incrementAndGet();
    }

    public int getRequestCount() {
        return requestCount.get();
    }
}