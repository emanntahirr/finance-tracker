package com.emantahir.finance_tracker.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MarketDataService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Value("${alphavantage.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Double getStockPrice(String symbol) {
        final double FALLBACK_PRICE = 100.0; 

        if (symbol == null || symbol.trim().isEmpty()) {
            return FALLBACK_PRICE;
        }

        try {
            // Core API Request URL Construction
            String url = apiUrl + "?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            // Check for API-side errors
            if (response != null && response.containsKey("Error Message")) {
                 System.err.println("Alpha Vantage API Error: " + response.get("Error Message"));
                 return FALLBACK_PRICE;
            }

            // JSON Parsing Logic
            if (response != null && response.containsKey("Global Quote")) {
                Map<String, Object> quote = (Map<String, Object>) response.get("Global Quote");
                
                if (quote != null && quote.containsKey("05. price")) {
                    String priceStr = (String) quote.get("05. price");
                    
                    if (priceStr == null || Double.parseDouble(priceStr) == 0.0) {
                        System.err.println("Stock symbol '" + symbol + "' not found or returned 0.0 price.");
                        return FALLBACK_PRICE;
                    }
                    
                    return Double.parseDouble(priceStr);
                }
            }
        } catch (HttpClientErrorException e) {
            System.err.println("HTTP Client Error: " + e.getStatusCode());
        } catch (Exception e) {
            System.err.println("Unexpected Error fetching stock price: " + e.getMessage());
        }
        
        return FALLBACK_PRICE;
    }
}