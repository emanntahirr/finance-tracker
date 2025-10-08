package com.emantahir.finance_tracker.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emantahir.finance_tracker.model.Investment;
import com.emantahir.finance_tracker.service.InvestmentService;

@RestController
@CrossOrigin(origins = {"https://finance-tracker-frontend.vercel.app", "http://localhost:3000"})
@RequestMapping("/api/investments")
public class InvestmentController {

    private final InvestmentService investmentService;

    public InvestmentController(InvestmentService investmentService) {
        this.investmentService = investmentService;
    }

    @PostMapping
    public ResponseEntity<Investment> createInvestment(@RequestBody Investment investment) {
        Investment newInvestment = investmentService.createInvestment(investment);
        return ResponseEntity.ok(newInvestment);
    }

    public ResponseEntity<Map<String, Double>> getPortfolioSummary() {
        Map<String, Double> summary = investmentService.getOverallPortfolioSummary();
        return ResponseEntity.ok(summary);
    }

    public ResponseEntity<List<Investment>> getAllInvestments() {
        List<Investment> investments = investmentService.getAllInvestments();
        return ResponseEntity.ok(investments);
    }

    @GetMapping
    public List<Investment> getInvestments() {
        return investmentService.getAllInvestments();
    }

    @PutMapping("/{id}/refresh")
    public Investment refreshInvestment(@PathVariable Long id) {
        return investmentService.refreshInvestment(id);
    }
    @PutMapping("/refresh-all")
    public List<Investment> refreshAll() {
        return investmentService.refreshAllInvestments();
    }
}
