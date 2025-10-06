package com.emantahir.finance_tracker.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.emantahir.finance_tracker.model.Investment;
import com.emantahir.finance_tracker.repository.InvestmentRepository;

@Service
public class InvestmentService {

    private final InvestmentRepository investmentRepository;
    private final MarketDataService marketDataService;
    private final TransactionService transactionService;

    public InvestmentService(
        InvestmentRepository investmentRepository,
        MarketDataService marketDataService,
        TransactionService transactionService) {

        this.investmentRepository = investmentRepository;
        this.marketDataService = marketDataService;
        this.transactionService = transactionService;
    }

    public Investment createInvestment(Investment investment) {
        
        if (investment.getAmountInvested() == null) {
            investment.setAmountInvested(0.0);
        }
        
        Double marketPricePerUnit = marketDataService.getStockPrice(investment.getStockSymbol());

        // The amount the user committed via the frontend form
        Double committedAmount = investment.getAmountInvested();
        
        // 2. Set prices using the committed amount and the live market price
        // purchasePrice is the initial value (the committed capital)
        investment.setPurchasePrice(committedAmount); 
        
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
}