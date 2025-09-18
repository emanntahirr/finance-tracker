package com.emantahir.finance_tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emantahir.finance_tracker.model.Transaction;
import com.emantahir.finance_tracker.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController { 

    private final TransactionService service;
    
    public TransactionController(TransactionService service) {
        this.service = service;
    } 

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @PostMapping 
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return service.addTransaction(transaction);
    }

    @GetMapping("/balance")
    public double getBalance() {
        return service.getBalance();
    }

    @GetMapping("/summary")
    public Map<String, Double> getIncomeExpenseSummary() {
        return service.getIncomeExpenseSummary();
    }

    @GetMapping("/income")
    public List<Transaction> getIncome() {
        return service.getIncomeTransactions();
    }
}
