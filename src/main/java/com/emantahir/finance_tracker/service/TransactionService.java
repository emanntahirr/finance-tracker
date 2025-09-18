package com.emantahir.finance_tracker.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.emantahir.finance_tracker.model.Transaction;
import com.emantahir.finance_tracker.repository.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction addTransaction(Transaction transaction) {
        return repository.save(transaction);
    }

    public double getBalance() {
        double income = 0;
        double expense = 0;

        List<Transaction> transactions = repository.findAll();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                income += t.getAmount();
            } else {
                expense += Math.abs(t.getAmount()); 
            }
        }

        return income - expense;
    }

    public Map<String, Double> getIncomeExpenseSummary() {
        double income = 0;
        double expense = 0;

        List<Transaction> transactions = repository.findAll();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                income += t.getAmount();
            } else {
                expense += Math.abs(t.getAmount());
            }
        }

        double balance = income - expense;

        Map<String, Double> summary = new HashMap<>();
        summary.put("income", income);
        summary.put("expenses", expense);
        summary.put("balance", balance);

        return summary;
    }

    public List<Transaction> getIncomeTransactions() {
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