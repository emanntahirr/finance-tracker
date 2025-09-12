package com.emantahir.finance_tracker.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emantahir.finance_tracker.model.Transaction;
import com.emantahir.finance_tracker.repository.TransactionRepository;

@RestController
@RequestMapping("/transactions")
public class TransactionController { 

    private final TransactionRepository repository;
    
    public TransactionController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() { // Changed from void to List<Transaction>
        return repository.findAll();
    }

    @PostMapping 
    public Transaction addTransaction(@RequestBody Transaction transaction) { // Changed from void to Transaction
        return repository.save(transaction);
    }

    @GetMapping("/balance")
    public double getBalance() { // New endpoint for balance
        double total = 0;

        List<Transaction> transactions = repository.findAll();

        for (Transaction t : transactions) {
            total += t.getAmount();
        }
        return total;
    }

    @GetMapping("/summary")
    public String getIncomeExpenseSummary() { // New endpoint for income and expense summary

        double income = 0;
        double expense = 0;

        List<Transaction> transactions = repository.findAll(); // Fetch all transactions
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                income += t.getAmount();
            } else {
                expense += t.getAmount();
            }
        }

        return "Income: "  + income + ", Expenes: " + expense;
    }

    @GetMapping("/income")
    public List<Transaction> getIncome() {  // New endpoint for income transactions
        List<Transaction> allTransactions = repository.findAll();
        List<Transaction> income = new ArrayList<>();

        for (Transaction t : allTransactions) {
            if (t.getAmount() > 0) {
                income.add(t);
            }
        }
        return income;
    }
}

