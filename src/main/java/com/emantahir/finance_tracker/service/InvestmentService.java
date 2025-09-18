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

        double currentBalance = transactionService.getBalance();
        if (investment.getAmountInvested() > currentBalance) {
            throw new RuntimeException("Insufficient funds to make this investment" + currentBalance);
        }
        Double price = marketDataService.getStockPrice(investment.getStockSymbol());

        if (price == null) {
            price = 100.0;
        }

        investment.setPurchasePrice(price);
        investment.setCurrentPrice(price);

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