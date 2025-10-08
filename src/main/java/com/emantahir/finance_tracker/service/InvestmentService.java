package com.emantahir.finance_tracker.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.emantahir.finance_tracker.model.Investment;
import com.emantahir.finance_tracker.repository.InvestmentRepository;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final MarketDataService marketDataService;

    public InvestmentService(
        InvestmentRepository investmentRepository,
        MarketDataService marketDataService)
        {

        this.investmentRepository = investmentRepository;
        this.marketDataService = marketDataService;
    }

    public Investment createInvestment(Investment investment) {
        
        if (investment.getAmountInvested() == null || investment.getAmountInvested() <= 0) {
            throw new IllegalArgumentException("Amount invested must be greater than zero.");
        }
        
        Double marketPricePerUnit = marketDataService.getStockPrice(investment.getStockSymbol());

        // The amount the user committed via the frontend form
        Double committedAmount = investment.getAmountInvested();

        Double quantityPurchased = committedAmount / marketPricePerUnit;
        
        // 2. Set prices using the committed amount and the live market price
        // purchasePrice is the initial value (the committed capital)
        investment.setPurchasePrice(committedAmount);
        
        investment.setQuantity(quantityPurchased);
        investment.setAveragePurchasePrice(marketPricePerUnit); // Cost basis per unit
        // currentPrice is the live price fetched from the API
        investment.setCurrentPrice(marketPricePerUnit);

        return investmentRepository.save(investment);
    }

    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    public Investment refreshInvestment(Long id) {
        Investment investment = investmentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Investment not found"));

        Double latestPrice = marketDataService.getStockPrice(investment.getStockSymbol());
        investment.setCurrentPrice(latestPrice);

        return investmentRepository.save(investment);
    }

    public List<Investment> refreshAllInvestments() {
    List<Investment> investments = investmentRepository.findAll();

    for (Investment inv : investments) {
        Double latestPrice = marketDataService.getStockPrice(inv.getStockSymbol());
        inv.setCurrentPrice(latestPrice);
    }

        return investmentRepository.saveAll(investments);
    }

    public Map<String, Double> getPortfolioSummary() {
        List<Investment> investments = investmentRepository.findAll();
        Double totalMarketValue = 0.0;
        Double totalCommittedCapital = 0.0;

        for (Investment investment : investments) {
            Double marketPricePerUnit = marketDataService.getStockPrice(investment.getStockSymbol());
            

            if (marketPricePerUnit != null || marketPricePerUnit <= 0) {
                System.err.println("Skipping invalid price for symbol: " + investment.getStockSymbol());
                continue;
            }
            Double marketValue = investment.getQuantity() * marketPricePerUnit;
            
            // Aggregate totals across all investments
            totalMarketValue += marketValue;
            totalCommittedCapital += investment.getPurchasePrice(); // The total cash paid (from the Investment entity)
        }

        Double totalProfitLoss = totalMarketValue - totalCommittedCapital;
        
        Map<String, Double> summary = new HashMap<>();
        summary.put("totalMarketValue", totalMarketValue);
        summary.put("totalCommittedCapital", totalCommittedCapital);
        summary.put("totalProfitLoss", totalProfitLoss);
        
        // Calculate percentage change (handling division by zero)
        Double percentageChange = (totalCommittedCapital > 0) 
            ? (totalProfitLoss / totalCommittedCapital) * 100 
            : 0.0;
            
        summary.put("percentageChange", percentageChange);

        return summary;
    }

    public Map<String, Double> getOverallPortfolioSummary() {
        List<Investment> allInvestments = investmentRepository.findAll();

        double totalInvested = 0.0;
        double totalCurrentValue = 0.0;

        for (Investment investment : allInvestments) {
            totalInvested += investment.getPurchasePrice();
            totalCurrentValue += investment.getCurrentPrice();
        }

        double totalProfitLoss = totalCurrentValue - totalInvested;

        return Map.of(
            "totalInvested", totalInvested,
            "totalCurrentValue", totalCurrentValue,
            "totalProfitLoss", totalProfitLoss
        );
    }
}