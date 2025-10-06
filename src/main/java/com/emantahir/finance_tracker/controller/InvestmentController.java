package com.emantahir.finance_tracker.controller;

import java.util.List;

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
    public Investment createInvestment(@RequestBody Investment investment) {
        // delegate to the service
        return investmentService.createInvestment(investment);
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
