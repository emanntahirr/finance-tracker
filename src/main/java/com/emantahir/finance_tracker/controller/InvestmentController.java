package com.emantahir.finance_tracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emantahir.finance_tracker.model.Investment;
import com.emantahir.finance_tracker.repository.InvestmentRepository;
import com.emantahir.finance_tracker.repository.TransactionRepository;

@RestController
@RequestMapping("/investments")
public class InvestmentController {

    private final InvestmentRepository investmentRepository;
    private final TransactionRepository transactionRepository;

    public InvestmentController(InvestmentRepository investmentRepository, 
                                TransactionRepository transactionRepository) {
        this.investmentRepository = investmentRepository;
        this.transactionRepository = transactionRepository;
    }

    // Add a new investment with balance check
    @PostMapping
    public Investment addInvestment(@RequestBody Investment investment) {
        double balance = transactionRepository.findAll()
                .stream()
                .mapToDouble(t -> t.getAmount())
                .sum();

        if (investment.getAmountInvested() > balance) {
            throw new IllegalArgumentException("Not enough balance to invest!");
        }

        return investmentRepository.save(investment);
    }

    // Get all investments
    @GetMapping
    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }

    // Get total value of all investments
    @GetMapping("/total")
    public double getTotalInvestmentValue() {
        double total = 0;
        for (Investment inv : investmentRepository.findAll()) {
            total += inv.getAmountInvested(); // use amount for now
        }
        return total;
    }
}
