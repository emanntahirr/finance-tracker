package com.emantahir.finance_tracker.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MarketDataService {

    private static final Logger logger = LoggerFactory.getLogger(MarketDataService.class);
    private static final String DEFAULT_BASE = "https://www.alphavantage.co/query";

    @Value("${alphavantage.api.key:}")        // injected from application.properties
    private String apiKey;

    @Value("${alphavantage.api.url:" + DEFAULT_BASE + "}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // Returns the latest quoted price (or -1 on failure)
    public double getStockPrice(String symbol) {
        try {
            String s = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
            String url = baseUrl + "?function=GLOBAL_QUOTE&symbol=" + s + "&apikey=" + apiKey;
            String resp = restTemplate.getForObject(url, String.class);
            if (resp == null) return -1;

            JsonNode root = mapper.readTree(resp);

            // handle rate-limit / error messages
            if (root.has("Note")) {
                logger.warn("AlphaVantage note: {}", root.get("Note").asText());
                return -1;
            }
            if (root.has("Error Message")) {
                logger.warn("AlphaVantage error: {}", root.get("Error Message").asText());
                return -1;
            }

            JsonNode quote = root.path("Global Quote");
            if (quote.isMissingNode() || quote.size() == 0) {
                logger.warn("No Global Quote for {}", symbol);
                return -1;
            }

            String priceStr = quote.path("05. price").asText(null);
            if (priceStr == null) {
                logger.warn("Price missing for {}", symbol);
                return -1;
            }
            return Double.parseDouble(priceStr);

        } catch (Exception e) {
            logger.error("getStockPrice failed for {}: {}", symbol, e.getMessage());
            return -1;
        }
    }

    // Returns the last `days` closing prices (oldest -> newest). Returns empty array on failure.
    public double[] getStockTrend(String symbol, int days) {
        try {
            String s = URLEncoder.encode(symbol, StandardCharsets.UTF_8);
            String url = baseUrl + "?function=TIME_SERIES_DAILY&symbol=" + s + "&apikey=" + apiKey + "&outputsize=compact";
            String resp = restTemplate.getForObject(url, String.class);
            if (resp == null) return new double[0];

            JsonNode root = mapper.readTree(resp);
            if (root.has("Note")) {
                logger.warn("AlphaVantage note: {}", root.get("Note").asText());
                return new double[0];
            }
            if (root.has("Error Message")) {
                logger.warn("AlphaVantage error: {}", root.get("Error Message").asText());
                return new double[0];
            }

            JsonNode series = root.path("Time Series (Daily)");
            if (series.isMissingNode() || series.size() == 0) {
                logger.warn("No time series for {}", symbol);
                return new double[0];
            }

            List<String> dates = new ArrayList<>();
            series.fieldNames().forEachRemaining(dates::add);
            Collections.sort(dates, Collections.reverseOrder());

            int take = Math.min(days, dates.size());
            double[] trend = new double[take];


            for (int i = 0; i < take; i++) {
                String date = dates.get(i); // newest-first
                String closeStr = series.path(date).path("4. close").asText();
                double close = Double.parseDouble(closeStr);
                trend[take - 1 - i] = close; // store oldest->newest
            }
            return trend;

        } catch (Exception e) {
            logger.error("getStockTrend failed for {}: {}", symbol, e.getMessage());
            return new double[0];
        }
    }


    public double[] getStockTrend(String symbol) {
        return getStockTrend(symbol, 5);
    }
}
