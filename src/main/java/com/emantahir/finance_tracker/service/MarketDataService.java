package com.emantahir.finance_tracker.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MarketDataService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Value("${alphavantage.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public Double getStockPrice(String symbol) {
        try {
            String url = apiUrl + "?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            System.out.println("API Response: " + response); // 👈 Debugging

            if (response != null && response.containsKey("Global Quote")) {
                Map<String, Object> quote = (Map<String, Object>) response.get("Global Quote");
                if (quote != null && quote.containsKey("05. price")) {
                    String priceStr = (String) quote.get("05. price");
                    return Double.parseDouble(priceStr);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching stock price: " + e.getMessage());
        }
        return 100.0;
    }
}
