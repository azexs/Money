package com.example.Currency.service.impl;

import com.example.Currency.model.response.RateResponse;
import com.example.Currency.service.IRatesService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service(value = "ratesCacheService")
@CacheConfig(cacheNames = "RatesCache")
public class RatesCacheService implements IRatesService {


    private final CacheManager cacheManager;
    private final RatesService ratesService;

    public RatesCacheService(CacheManager cacheManager,
                             RatesService ratesService) {
        this.cacheManager = cacheManager;
        this.ratesService = ratesService;

    }

    @Override
    @Cacheable(value = "RatesCache", key = "'allRates'")
    public List<RateResponse> getExchangeRatesList() {
        return ratesService.getExchangeRatesList();


    }

    @Override
    @Cacheable(value = "RatesCache", key = "'rates_' + #date")
    public List<RateResponse> getExchangeRatesList(LocalDate publishDate) {
        return ratesService.getExchangeRatesList(publishDate);
    }

    @Override
    public RateResponse getExchangeRate(String currencyCode) {
        List<RateResponse> rateResponses = getRatesFromCache();
        if (rateResponses != null) {
            return rateResponses.stream()
                    .filter(rate -> currencyCode.equals(rate.getCode()))
                    .findFirst()
                    .orElse(null);
        }
        return ratesService.getExchangeRate(currencyCode);

    }

    @Override
    public RateResponse updateExchangeRates(String currencyCode) {
        RateResponse rate = ratesService.updateExchangeRates(currencyCode);
        updateCacheWithRate(rate);

        //rabbitTemplate.convertAndSend(exchange, routingKey, message);
        return rate;
    }

    private List<RateResponse> getRatesFromCache() {
        Cache ratesCache = cacheManager.getCache("RatesCache");
        if (ratesCache != null) {
            Cache.ValueWrapper valueWrapper = ratesCache.get("allRates");
            if (valueWrapper != null) {
                return (List<RateResponse>) valueWrapper.get();
            }
        }
        return null;
    }

    private void updateCacheWithRate(RateResponse rateResponse) {
        Cache ratesCache = cacheManager.getCache("RatesCache");
        if (ratesCache != null) {
            List<RateResponse> rateResponseList = getRatesFromCache();
            if (rateResponseList == null) {
                rateResponseList = new ArrayList<>();
            }
            rateResponseList.add(rateResponse);
            ratesCache.put("allRates", rateResponseList);
        }
    }
}

