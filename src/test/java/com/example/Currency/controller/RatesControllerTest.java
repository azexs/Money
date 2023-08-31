package com.example.Currency.controller;

import com.example.Currency.model.response.RateResponse;
import com.example.Currency.service.IRatesService;
import com.example.Currency.utils.SharedData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(RatesController.class)
@Import(SharedData.class)
public class RatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("ratesCacheService")
    private IRatesService ratesService;

    @Autowired
    private SharedData sharedData;


    @Test
    public void testGetExchangeRatesList() throws Exception {
        when(ratesService.getExchangeRatesList()).thenReturn(Collections.singletonList(new RateResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/rates")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        verify(ratesService).getExchangeRatesList();
    }

    @Test
    public void testUpdateExchangeRate() throws Exception {
        String testCurrencyId = "USD";

        when(ratesService.updateExchangeRates(testCurrencyId)).thenReturn(new RateResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/refresh-cache/" + testCurrencyId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

        assertEquals(1, sharedData.getRequestCount());
    }

}