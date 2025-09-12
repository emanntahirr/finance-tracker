package com.emantahir.finance_tracker.model;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Investment {
// fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Double amountInvested;
    private LocalDate investedDate;
    private Double expectedReturnRate;
    private Double currentValue;

    public Investment(String name, Double amountInvested, LocalDate investedDate, Double expectedReturnRate, Double currentValue) {
        this.name = name;
        this.amountInvested = amountInvested;
        this.investedDate = investedDate;
        this.expectedReturnRate = expectedReturnRate;
        this.currentValue = currentValue;
    }

// getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(Double amountInvested) {
        this.amountInvested = amountInvested;
    }

    public LocalDate getInvestedDate() {
        return investedDate;
    }

    public void setInvestedDate(LocalDate investedDate) {
        this.investedDate = investedDate;
    }

    public Double getExpectedReturnRate() {
        return expectedReturnRate;
    }

    public void setExpectedReturnRate(Double expectedReturnRate) {
        this.expectedReturnRate = expectedReturnRate;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }
}